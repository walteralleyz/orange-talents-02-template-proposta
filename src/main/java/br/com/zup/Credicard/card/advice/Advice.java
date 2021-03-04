package br.com.zup.Credicard.card.advice;

import br.com.zup.Credicard.card.Card;
import br.com.zup.Credicard.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "advices")
public class Advice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;

    @NotBlank
    private String destino;

    @ManyToOne
    private Card card;

    @OneToOne(cascade = CascadeType.MERGE)
    private User user;

    @Deprecated
    private Advice() {}

    public Advice(LocalDate validoAte, String destino, User user) {
        this.validoAte = validoAte;
        this.destino = destino;
        this.user = user;
    }

    public AdviceRequest toDTO() {
        return new AdviceRequest(
            destino,
            validoAte
        );
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public User getUser() {
        return user;
    }
}
