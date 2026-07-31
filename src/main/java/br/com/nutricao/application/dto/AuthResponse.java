package br.com.nutricao.application.dto;

public class AuthResponse {

    private Integer id;
    private String nome;
    private String email;
    private String token;
    private String tipo;
    private String sexo;
    private String mensagem;

    public AuthResponse() {
    }

    public AuthResponse(Integer id, String nome, String email, String token, String tipo,
                        String sexo, String mensagem) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
        this.tipo = tipo;
        this.sexo = sexo;
        this.mensagem = mensagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
