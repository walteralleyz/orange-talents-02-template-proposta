package br.com.zup.Credicard.wallet;

import br.com.zup.Credicard.card.Card;
import br.com.zup.Credicard.card.CardClient;
import br.com.zup.Credicard.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

import static br.com.zup.Credicard.card.Card.findCardById;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CardClient cardClient;

    @PostMapping("/card/{id}")
    @Transactional
    public ResponseEntity<?> wallet(
        @PathVariable String id,
        @RequestBody @Valid WalletRequest request,
        UriComponentsBuilder builder
    ) {
        Card card = findCardById(id, em);

        try {
            WalletResponse response = cardClient.wallet(id, request);

            if(response.isAssociated()) {
                WalletDTO dto = new WalletDTO(
                    response.getId(),
                    request.getEmail(),
                    LocalDateTime.now(),
                    request.getCarteira()
                );

                card.setCarteiras(dto);

                em.merge(card);
            }

            URI uri = builder.path("/api/wallet/{id}").buildAndExpand(response.getId()).toUri();

            return ResponseEntity.created(uri).build();

        } catch (Exception e) {
            throw new DomainException("wallet", "Card");
        }
    }
}
