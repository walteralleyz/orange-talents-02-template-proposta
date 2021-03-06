package br.com.zup.Credicard.wallet;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class WalletDTO {
    @NotBlank
    private final String id;

    @NotBlank
    private final String email;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
    private final LocalDateTime associadoEm;

    @Enumerated(EnumType.STRING)
    private WalletType emissor;

    public WalletDTO(String id, String email, LocalDateTime associadoEm, WalletType emissor) {
        this.id = id;
        this.email = email;
        this.associadoEm = associadoEm;
        this.emissor = emissor;
    }

    public Wallet toModel() {
        return new Wallet(
          id,
          email,
          associadoEm,
          emissor
        );
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
