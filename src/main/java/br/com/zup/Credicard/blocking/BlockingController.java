package br.com.zup.Credicard.blocking;

import br.com.zup.Credicard.card.Card;
import br.com.zup.Credicard.card.CardClient;
import br.com.zup.Credicard.card.CardResponse;
import br.com.zup.Credicard.exception.DomainException;
import br.com.zup.Credicard.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import static br.com.zup.Credicard.card.Card.findCardById;

@RestController
@RequestMapping("/api/blocking")
public class BlockingController {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CardClient cardClient;

    @GetMapping("/card/{id}")
    @Transactional
    public ResponseEntity<?> block(
        @PathVariable String id,
        @RequestHeader("user-agent") String agent,
        HttpServletRequest request
    ) {
        Card card = findCardById(id, em);

        try {
            BlockingResponse response = cardClient.block(id, new BlockingRequest("proposal"));
            CardResponse cardResponse = cardClient.cardById(id);

            if(response.isBlocked()) {
                BlockingDTO dto = (BlockingDTO) cardResponse.getLastElement("blocking");
                dto.setUser(new UserRequest(request.getRemoteAddr(), agent));
                card.setBloqueios(dto);

                em.merge(card);
            }
        } catch (Exception e) {
            throw new DomainException("blocking", "Card");
        }

        return ResponseEntity.ok().build();
    }
}
