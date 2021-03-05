package br.com.zup.Credicard.security;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    private String client_id;
    private String client_secret;

    public LoginRequest(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getGrant_type() {
        return "password";
    }
}
