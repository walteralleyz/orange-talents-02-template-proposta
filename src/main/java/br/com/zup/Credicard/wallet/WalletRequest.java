package br.com.zup.Credicard.wallet;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class WalletRequest {
    @Email
    @NotBlank
    private final String email;

    @Enumerated(EnumType.STRING)
    private final WalletType carteira;

    public WalletRequest(@Email @NotBlank String email, WalletType carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public WalletType getCarteira() {
        return carteira;
    }
}

