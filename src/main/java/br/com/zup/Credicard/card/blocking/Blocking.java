package br.com.zup.Credicard.card.blocking;

import br.com.zup.Credicard.card.Card;
import br.com.zup.Credicard.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "blocks")
public class Blocking {
    @Id
    @NotNull
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
    private LocalDateTime bloqueadoEm;

    @NotBlank
    private String sistemaResponsavel;

    @NotNull
    private boolean ativo;

    @ManyToOne
    private Card card;

    @OneToOne(cascade = CascadeType.MERGE)
    private User user;

    @Deprecated
    private Blocking() {}

    public Blocking(
        String id,
        LocalDateTime bloqueadoEm,
        String sistemaResponsavel,
        boolean ativo,
        User user
    ) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
        this.user = user;
    }

    public BlockingDTO toDTO() {
        return new BlockingDTO(
            id,
            bloqueadoEm,
            sistemaResponsavel,
            ativo
        );
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public User getUser() {
        return user;
    }
}
