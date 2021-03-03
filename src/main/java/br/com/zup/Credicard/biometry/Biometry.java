package br.com.zup.Credicard.biometry;

import br.com.zup.Credicard.card.Card;

import javax.persistence.*;

@Entity
@Table(name = "biometries")
public class Biometry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Card card;

    @Column(nullable = false)
    private String fingerprint;

    @Deprecated
    private Biometry() {}

    public Biometry(Card card, String fingerprint) {
        this.card = card;
        this.fingerprint = fingerprint;
    }

    public BiometryResponse toDTO() {
        return new BiometryResponse(
            card,
            fingerprint
        );
    }

    public Long getId() {
        return id;
    }

    public Card getCard() {
        return card;
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
