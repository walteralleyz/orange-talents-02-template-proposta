package br.com.zup.Credicard.advice;

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
@RequestMapping("/api/advice")
public class AdviceController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CardClient cardClient;

    @PostMapping("/card/{id}")
    @Transactional
    public ResponseEntity<?> travel(
        @PathVariable String id,
        @RequestBody AdviceRequest request,
        @RequestHeader("user-agent") String agent,
        HttpServletRequest servletRequest
    ) {
        Card card = findCardById(id, em);

        try {
            AdviceResponse response = cardClient.travel(id, request);
            CardResponse cardResponse = cardClient.cardById(id);

            if(response.isAdviced()) {
                AdviceRequest dto = (AdviceRequest) cardResponse.getLastElement("advice");
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
