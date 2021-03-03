package br.com.zup.Credicard.biometry;

import br.com.zup.Credicard.card.Card;

public class BiometryResponse {
    private final Card card;
    private final String fingerprint;

    public BiometryResponse(Card card, String fingerprint) {
        this.card = card;
        this.fingerprint = fingerprint;
    }

    public Card getCard() {
        return card;
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
