package br.com.zup.Credicard.biometry;

import br.com.zup.Credicard.card.Card;
import br.com.zup.Credicard.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import java.util.Base64;
import java.util.Optional;

public class BiometryRequest {
    @NotBlank
    private String fingerprint;
    private Card card;

    @Deprecated
    public BiometryRequest() {}

    public BiometryRequest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Biometry toModel() {
        return new Biometry(
            card,
            Base64.getEncoder().encodeToString(fingerprint.getBytes())
        );
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(String card, EntityManager em) {
        this.card = Optional.ofNullable(em.find(Card.class, card))
            .orElseThrow(() -> new NotFoundException("Card"));
    }
}
