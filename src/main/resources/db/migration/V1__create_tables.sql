CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    senha VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    data_nascimento DATE,
    altura DECIMAL(10,2),
    sexo VARCHAR(50) NOT NULL,
    data_cadastro DATETIME NOT NULL,
    medida VARCHAR(10) NOT NULL,
    tipo_login VARCHAR(50) NOT NULL,
    token_id VARCHAR(2000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE categoria_alimento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE alimento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    kcal VARCHAR(10),
    proteina VARCHAR(10),
    gordura VARCHAR(10),
    carboidrato VARCHAR(10),
    fibra_alimentar VARCHAR(10),
    sodio VARCHAR(10),
    categoria_alimento_id INT NOT NULL,
    CONSTRAINT fk_alimento_categoria FOREIGN KEY (categoria_alimento_id) REFERENCES categoria_alimento(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE refeicao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    data_refeicao DATETIME NOT NULL,
    usuario_id INT NOT NULL,
    CONSTRAINT fk_refeicao_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE alimento_refeicao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    refeicao_id INT NOT NULL,
    alimento_id INT NOT NULL,
    quantidade DECIMAL(10,2),
    porcao VARCHAR(100),
    CONSTRAINT fk_alimento_refeicao_refeicao FOREIGN KEY (refeicao_id) REFERENCES refeicao(id),
    CONSTRAINT fk_alimento_refeicao_alimento FOREIGN KEY (alimento_id) REFERENCES alimento(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE meta_nutricional (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    calorias DECIMAL(10,2),
    proteinas DECIMAL(10,2),
    carboidratos DECIMAL(10,2),
    gorduras DECIMAL(10,2),
    data_inicio DATE NOT NULL,
    data_fim DATE,
    CONSTRAINT fk_meta_nutricional_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE objetivo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    peso_alvo DECIMAL(10,2),
    calorias_diarias DECIMAL(10,2),
    data_inicio DATE NOT NULL,
    data_fim DATE,
    descricao TEXT,
    CONSTRAINT fk_objetivo_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE registro_diario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    data DATE NOT NULL,
    calorias_consumidas DECIMAL(10,2),
    proteinas_consumidas DECIMAL(10,2),
    carboidratos_consumidos DECIMAL(10,2),
    gorduras_consumidas DECIMAL(10,2),
    observacoes TEXT,
    CONSTRAINT fk_registro_diario_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE alimento_favorito (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    alimento_id INT NOT NULL,
    data_adicao DATETIME NOT NULL,
    CONSTRAINT fk_alimento_favorito_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_alimento_favorito_alimento FOREIGN KEY (alimento_id) REFERENCES alimento(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
