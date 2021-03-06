package br.com.zup.Credicard.wallet;

import br.com.zup.Credicard.card.Card;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @NotBlank
    private String id;

    @Email
    @NotBlank
    private String email;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private LocalDateTime associadoEm;

    @Enumerated(EnumType.STRING)
    private WalletType emissor;

    @ManyToOne
    private Card card;

    @Deprecated
    private Wallet() {}

    public Wallet(
        String id,
        String email,
        LocalDateTime associadoEm,
        WalletType emissor
    ) {
        this.id = id;
        this.email = email;
        this.associadoEm = associadoEm;
        this.emissor = emissor;
    }

    public WalletDTO toDTO() {
        return new WalletDTO(
            id,
            email,
            associadoEm,
            emissor
        );
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadoEm() {
        return associadoEm;
    }

    public WalletType getEmissor() {
        return emissor;
    }
}
