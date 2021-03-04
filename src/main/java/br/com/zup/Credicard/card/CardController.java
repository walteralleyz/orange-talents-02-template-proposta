package br.com.zup.Credicard.card;

import br.com.zup.Credicard.card.advice.AdviceRequest;
import br.com.zup.Credicard.card.advice.AdviceResponse;
import br.com.zup.Credicard.card.blocking.BlockingDTO;
import br.com.zup.Credicard.card.blocking.BlockingRequest;
import br.com.zup.Credicard.card.blocking.BlockingResponse;
import br.com.zup.Credicard.exception.DomainException;
import br.com.zup.Credicard.exception.NotFoundException;
import br.com.zup.Credicard.user.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> block(
        @PathVariable String id,
        @RequestHeader("user-agent") String agent,
        HttpServletRequest request
    ) {
        Card card = Optional.ofNullable(em.find(Card.class, id))
            .orElseThrow(() -> new NotFoundException("Card"));

        try {
            BlockingResponse response = cardClient.block(id, new BlockingRequest("proposal"));
            CardResponse cardResponse = cardClient.cardById(id);

            if(response.getResultado().equals("BLOQUEADO")) {
                int lastIndex = cardResponse.getBloqueios().size() - 1;
                BlockingDTO dto = cardResponse.getBloqueios().get(lastIndex);
                dto.setUser(new UserRequest(request.getRemoteAddr(), agent));
                card.setBloqueios(dto);

                em.merge(card);
            }
        } catch (Exception e) {
            throw new DomainException("blocking", "Card");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/travel")
    @Transactional
    public ResponseEntity<?> travel(
        @PathVariable String id,
        @RequestBody AdviceRequest request,
        @RequestHeader("user-agent") String agent,
        HttpServletRequest servletRequest
    ) {
        Card card = Optional.ofNullable(em.find(Card.class, id))
            .orElseThrow(() -> new NotFoundException("Card"));
        CardResponse cardResponse;
        AdviceResponse response;

        try {
            response = cardClient.travel(id, request);
            cardResponse = cardClient.cardById(id);

            if(response.getResultado().equals("CRIADO")) {
                int lastIndex = cardResponse.getAvisos().size() - 1;
                AdviceRequest dto = cardResponse.getAvisos().get(lastIndex);
                dto.setUserRequest(new UserRequest(servletRequest.getRemoteAddr(), agent));
                card.setAvisos(dto);

                em.merge(card);
            }

        } catch (Exception e) {
            throw new DomainException("advice", "Card");
        }

        return ResponseEntity.ok().build();
    }
}
