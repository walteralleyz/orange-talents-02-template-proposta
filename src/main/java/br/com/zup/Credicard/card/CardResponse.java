package br.com.zup.Credicard.card;

import br.com.zup.Credicard.biometry.Biometry;
import br.com.zup.Credicard.card.advice.Advice;
import br.com.zup.Credicard.card.blocking.Blocking;
import br.com.zup.Credicard.card.installment.Installment;
import br.com.zup.Credicard.card.wallet.Wallet;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CardResponse {
    private final String id;
    private final String idProposta;
    private final BigDecimal limite;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss", shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
    private final LocalDateTime emitidoEm;

    private final String titular;
    private final List<Blocking> bloqueios;
    private final List<Advice> avisos;
    private final List<Wallet> carteiras;
    private final List<Installment> parcelas;
    private final List<Biometry> biometries;

    public CardResponse(
        String id,
        LocalDateTime emitidoEm,
        String titular,
        List<Blocking> bloqueios,
        List<Advice> avisos,
        List<Wallet> carteiras,
        List<Installment> parcelas,
        String idProposta,
        BigDecimal limite,
        List<Biometry> biometries
    ) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.idProposta = idProposta;
        this.limite = limite;
        this.parcelas = parcelas;
        this.biometries = biometries;
    }

    public Card toModel(EntityManager em) {
        Card card = new Card(
            id,
            emitidoEm,
            titular,
            bloqueios,
            avisos,
            carteiras,
            parcelas,
            idProposta,
            limite
        );

        em.persist(card);

        return card;
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

    public List<Blocking> getBloqueios() {
        return bloqueios;
    }

    public List<Advice> getAvisos() {
        return avisos;
    }

    public List<Wallet> getCarteiras() {
        return carteiras;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public List<Installment> getParcelas() {
        return parcelas;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public List<Biometry> getBiometries() {
        return biometries;
    }
}