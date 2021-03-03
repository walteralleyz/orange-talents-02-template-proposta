package br.com.zup.Credicard.card;

import br.com.zup.Credicard.biometry.Biometry;
import br.com.zup.Credicard.card.advice.Advice;
import br.com.zup.Credicard.card.blocking.Blocking;
import br.com.zup.Credicard.card.installment.Installment;
import br.com.zup.Credicard.card.wallet.Wallet;
import br.com.zup.Credicard.proposal.Proposal;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    private String id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyy-MM-dd@HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
    private LocalDateTime emitidoEm;

    @Column(nullable = false)
    private String titular;

    @OneToMany(mappedBy = "card")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<Blocking> bloqueios;

    @OneToMany(mappedBy = "card")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<Advice> avisos;

    @OneToMany(mappedBy = "card")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<Wallet> carteiras;

    @OneToMany(mappedBy = "card")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<Installment> parcelas;

    @OneToMany(mappedBy = "card")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<Biometry> biometries;

    private String idProposta;

    private BigDecimal limite;

    @Deprecated
    private Card() {}

    public Card(
        String id,
        LocalDateTime emitidoEm,
        String titular,
        List<Blocking> bloqueios,
        List<Advice> avisos,
        List<Wallet> carteiras,
        List<Installment> parcelas,
        String idProposta,
        BigDecimal limite
    ) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    public CardResponse toDTO() {
        return new CardResponse(
            id,
            emitidoEm,
            titular,
            bloqueios,
            avisos,
            carteiras,
            parcelas,
            idProposta,
            limite,
            biometries
        );
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public List<Advice> getAvisos() {
        return avisos;
    }

    public List<Blocking> getBloqueios() {
        return bloqueios;
    }

    public List<Installment> getParcelas() {
        return parcelas;
    }

    public List<Wallet> getCarteiras() {
        return carteiras;
    }

    public List<Biometry> getBiometries() {
        return biometries;
    }
}
