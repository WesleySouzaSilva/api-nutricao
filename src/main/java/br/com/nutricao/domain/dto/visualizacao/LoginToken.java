package br.com.nutricao.application.dto;

public class LoginToken {

    private String tokenId;

    public LoginToken() {
    }

    public LoginToken(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
