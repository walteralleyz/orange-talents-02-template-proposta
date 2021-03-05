package br.com.zup.Credicard.wallet;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class WalletRequest {
    @Email
    @NotBlank
    private final String email;

    @NotBlank
    private final String carteira;

    public WalletRequest(@Email @NotBlank String email, @NotBlank String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}

