# Deploy em Producao — AWS EC2

Este documento descreve o processo completo de deploy da API em uma instancia EC2 na AWS, utilizando Nginx como reverse proxy, dominio proprio e certificado SSL via Certbot (Let's Encrypt).

> **Contexto**: Este deploy foi realizado em 2023, antes da adocao de containers. A experiencia de configurar manualmente uma VM Linux trouxe um aprendizado valioso sobre infraestrutura, redes e seguranca — conhecimento fundamental que depois facilitou a transicao para Docker e orquestracao.

---

## Arquitetura do Deploy

```
Internet (HTTPS)
     │
     ▼
┌─────────────────┐
│  Route 53 (DNS) │  ← dominio api.exemplo.com → IP publico da EC2
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   AWS EC2       │
│   (t2.micro)    │
│                 │
│  ┌───────────┐  │
│  │  Nginx    │  │  ← reverse proxy (porta 80/443 → 8080)
│  │  (HTTPS)  │  │
│  └─────┬─────┘  │
│        │         │
│  ┌─────▼─────┐  │
│  │ Spring    │  │  ← aplicacao Java (porta 8080)
│  │ Boot App  │  │
│  └─────┬─────┘  │
│        │         │
│  ┌─────▼─────┐  │
│  │  MySQL    │  │  ← banco de dados (porta 3306, localhost)
│  └───────────┘  │
└─────────────────┘
```

---

## 1. Criar Instancia EC2

### 1.1. Configuracao da Instancia

| Parametro | Valor |
|---|---|
| **AMI** | Ubuntu Server 20.04 LTS (ou 22.04) |
| **Tipo** | t2.micro (free tier) |
| **Storage** | 20 GB GP2 |
| **Security Group** | Ver secao abaixo |

### 1.2. Security Group (Firewall)

| Tipo | Porta | Origem | Descricao |
|---|---|---|---|
| SSH | 22 | Meu IP | Acesso remoto seguro |
| HTTP | 80 | 0.0.0.0/0 | Redirecionamento → HTTPS |
| HTTPS | 443 | 0.0.0.0/0 | Trafego seguro |
| Custom TCP | 8080 | 127.0.0.1/32 | Apenas Nginx local acessa a API |

> **Importante**: A porta 8080 da aplicacao Spring Boot **nunca** deve ficar aberta ao publico. Apenas o Nginx (localhost) acessa a API. Toda conexao externa passa pelo Nginx nas portas 80/443.

---

## 2. Configurar o Servidor

### 2.1. Conectar via SSH

```bash
ssh -i "caminho/para/chave.pem" ubuntu@<ip-publico-da-ec2>
```

### 2.2. Atualizar pacotes

```bash
sudo apt update && sudo apt upgrade -y
```

### 2.3. Instalar Java 11

```bash
sudo apt install openjdk-11-jdk -y
java -version
```

### 2.4. Instalar Maven

```bash
sudo apt install maven -y
mvn -version
```

### 2.5. Instalar Git

```bash
sudo apt install git -y
```

### 2.6. Instalar MySQL 8

```bash
sudo apt install mysql-server -y
sudo systemctl start mysql
sudo systemctl enable mysql

# Configurar usuario e banco
sudo mysql -u root <<SQL
CREATE DATABASE nutricao_prod;
CREATE USER 'nutricao'@'localhost' IDENTIFIED BY 'senha_segura';
GRANT ALL PRIVILEGES ON nutricao_prod.* TO 'nutricao'@'localhost';
FLUSH PRIVILEGES;
SQL
```

### 2.7. Instalar Nginx

```bash
sudo apt install nginx -y
sudo systemctl start nginx
sudo systemctl enable nginx
```

---

## 3. Clonar e Compilar o Projeto

```bash
cd /home/ubuntu
git clone https://github.com/WesleySouzaSilva/api-nutricao.git
cd api-nutricao

# Criar arquivo de configuracao de producao
cat > src/main/resources/application-prod.properties <<'EOF'
spring.datasource.url=jdbc:mysql://localhost:3306/nutricao_prod?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=nutricao
spring.datasource.password=senha_segura
spring.jpa.show-sql=false
spring.flyway.locations=classpath:db/migration
api.security.token.secret=${JWT_SECRET}
EOF

# Compilar e empacotar
mvn clean package -DskipTests
```

O JAR sera gerado em `target/api-nutricao-*.jar`.

---

## 4. Configurar o Servico Systemd

Criar um servico para que a aplicacao inicie automaticamente e reinicie em caso de falha:

```bash
sudo cat > /etc/systemd/system/api-nutricao.service <<'EOF'
[Unit]
Description=API Nutricao
After=network.target mysql.service

[Service]
User=ubuntu
WorkingDirectory=/home/ubuntu/api-nutricao
ExecStart=/usr/bin/java -jar /home/ubuntu/api-nutricao/target/api-nutricao-*.jar --spring.profiles.active=prod
Restart=always
RestartSec=10
Environment="JWT_SECRET=seu_segredo_jwt_aqui"

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload
sudo systemctl start api-nutricao
sudo systemctl enable api-nutricao
sudo systemctl status api-nutricao
```

> **Variaveis de ambiente**: O segredo JWT e passado via `Environment` no systemd, nunca hardcoded no codigo ou no application.properties.

---

## 5. Configurar Nginx como Reverse Proxy

### 5.1. Criar configuracao do site

```bash
sudo cat > /etc/nginx/sites-available/api-nutricao <<'EOF'
server {
    listen 80;
    server_name api.exemplo.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
EOF
```

### 5.2. Ativar o site

```bash
sudo ln -s /etc/nginx/sites-available/api-nutricao /etc/nginx/sites-enabled/
sudo rm /etc/nginx/sites-enabled/default
sudo nginx -t          # validar sintaxe
sudo systemctl reload nginx
```

---

## 6. Vincular Dominio (Route 53)

1. No painel da AWS, acessar **Route 53** → **Hosted Zones**
2. Criar registro **A** apontando para o IP publico da EC2:
   - **Nome**: `api.exemplo.com` (ou `api` se o dominio ja existir)
   - **Tipo**: A
   - **Valor**: `<ip-publico-da-ec2>`
   - **TTL**: 300

Apos propagacao do DNS (pode levar alguns minutos), a API ja responde em `http://api.exemplo.com`.

---

## 7. Configurar HTTPS com Certbot (Let's Encrypt)

### 7.1. Instalar Certbot

```bash
sudo apt install certbot python3-certbot-nginx -y
```

### 7.2. Gerar certificado SSL

```bash
sudo certbot --nginx -d api.exemplo.com
```

O Certbot ira:
1. Validar o dominio via desafio HTTP
2. Obter o certificado SSL/TLS da Let's Encrypt
3. Modificar a configuracao do Nginx automaticamente para HTTPS
4. Configurar redirecionamento HTTP → HTTPS

### 7.3. Verificar renovacao automatica

```bash
sudo certbot renew --dry-run
```

O Certbot configura um timer systemd que renova o certificado automaticamente a cada 60 dias.

### 7.4. Configuracao final do Nginx (apos Certbot)

O arquivo em `/etc/nginx/sites-available/api-nutricao` ficara similar a:

```nginx
server {
    server_name api.exemplo.com;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    listen 443 ssl;
    ssl_certificate /etc/letsencrypt/live/api.exemplo.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/api.exemplo.com/privkey.pem;
}

server {
    if ($host = api.exemplo.com) {
        return 301 https://$host$request_uri;
    }

    listen 80;
    server_name api.exemplo.com;
    return 404;
}
```

---

## 8. Comandos Uteis para Manutencao

```bash
# Status da aplicacao
sudo systemctl status api-nutricao

# Logs da aplicacao
sudo journalctl -u api-nutricao -f

# Reiniciar a aplicacao apos deploy
sudo systemctl restart api-nutricao

# Verificar logs do Nginx
sudo tail -f /var/log/nginx/access.log
sudo tail -f /var/log/nginx/error.log

# Testar configuracao do Nginx
sudo nginx -t

# Renovar certificado manualmente
sudo certbot renew
```

---

## 9. Atualizacao da Aplicacao (Deploy Continuo Manual)

```bash
cd /home/ubuntu/api-nutricao
git pull origin main
mvn clean package -DskipTests
sudo systemctl restart api-nutricao
```

---

## Aprendizados

Este deploy manual em VM trouxe diversos aprendizados fundamentais:

| Conceito | O que aprendi |
|---|---|
| **Redes e Firewall** | Security Groups da AWS, diferenca entre IP publico e privado, exposicao controlada de portas |
| **DNS** | Propagacao DNS, registros tipo A, vinculacao de dominio com Route 53 |
| **Reverse Proxy** | Nginx redirecionando trafego externo para a aplicacao interna, headers HTTP |
| **HTTPS/TLS** | Certificados SSL com Let's Encrypt, renovacao automatica, Certbot |
| **Systemd** | Gerenciamento de servicos Linux, restart automatico, logs via journalctl |
| **Seguranca** | Variaveis de ambiente para secrets, porta 8080 bloqueada externamente |

Esse conhecimento foi essencial para depois compreender o que containers e orquestradores fazem por tras das cenas — o Docker e o Kubernetes automatizam muitos desses passos, mas entender o funcionamento manual da a base para diagnosticar problemas e tomar decisoes de arquitetura.

---

[← Voltar ao README principal](../README.md)
