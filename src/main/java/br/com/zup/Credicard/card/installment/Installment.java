package br.com.zup.Credicard.card.installment;

import br.com.zup.Credicard.card.Card;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "installments")
public class Installment {
    @Id
    @NotBlank
    private String id;

    @NotNull
    private int quantidade;

    @NotNull
    @Positive
    private BigDecimal valor;

    @ManyToOne
    private Card card;

    @Deprecated
    private Installment() {}

    public Installment(
        String id,
        int quantidade,
        BigDecimal valor
    ) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
