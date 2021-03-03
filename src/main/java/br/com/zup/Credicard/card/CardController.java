package br.com.zup.Credicard.card;

import br.com.zup.Credicard.card.blocking.BlockingRequest;
import br.com.zup.Credicard.card.blocking.BlockingResponse;
import br.com.zup.Credicard.exception.DomainException;
import br.com.zup.Credicard.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CardClient cardClient;

    @GetMapping("/{id}/block")
    @Transactional
    public ResponseEntity<?> block(@PathVariable String id) {
        Card card = Optional.ofNullable(em.find(Card.class, id))
            .orElseThrow(() -> new NotFoundException("Card"));

        try {
            BlockingResponse response = cardClient.block(id, new BlockingRequest("proposal"));
            CardResponse cardResponse = cardClient.cardById(id);

            if(response.getResultado().equals("BLOQUEADO")) {
                int lastIndex = cardResponse.getBloqueios().size() - 1;
                card.setBloqueios(cardResponse.getBloqueios().get(lastIndex));

                em.merge(card);
            }
        } catch (Exception e) {
            throw new DomainException("blocking", "Card");
        }

        return ResponseEntity.ok().build();
    }
}
