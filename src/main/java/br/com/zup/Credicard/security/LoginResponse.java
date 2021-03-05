package br.com.zup.Credicard.security;

public class LoginResponse {
    private String access_token;
    private String token_type;

    public LoginResponse() {}

    public LoginResponse(String access_token, String token_type) {
        this.access_token = access_token;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }
}
