package br.com.zup.Credicard.user;

import javax.validation.constraints.NotBlank;

public class UserRequest {
    @NotBlank
    private final String ip;

    @NotBlank
    private final String userAgent;

    public UserRequest(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public User toModel() {
        return new User(
            ip,
            userAgent
        );
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
