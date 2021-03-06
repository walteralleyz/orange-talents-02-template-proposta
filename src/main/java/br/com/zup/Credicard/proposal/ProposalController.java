package br.com.zup.Credicard.proposal;

import br.com.zup.Credicard.card.CardClient;
import br.com.zup.Credicard.card.CardResponse;
import br.com.zup.Credicard.exception.NotFoundException;
import br.com.zup.Credicard.status.StatusClient;
import br.com.zup.Credicard.status.StatusResponse;
import br.com.zup.Credicard.status.StatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/proposal")
public class ProposalController {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StatusClient statusClient;

    @Autowired
    private CardClient cardClient;

    List<Long> proposalIds = new ArrayList<>();

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(
        @RequestBody @Valid ProposalRequest request,
        UriComponentsBuilder builder
    ) {
        Proposal proposal = request.toModel();
        URI uri;

        em.persist(proposal);
        uri = builder.path("/api/proposal/{id}").buildAndExpand(proposal.getId()).toUri();

        try {
            StatusResponse response = statusClient.status(proposal.toStatus());
            proposal.setProposalStatus(response.getResultadoSolicitacao());

            if(proposal.isElegivel())
                proposalIds.add(proposal.getId());

            return ResponseEntity.status(201).location(uri).build();
        } catch (Exception e) {
            proposal.setProposalStatus(StatusType.COM_RESTRICAO);
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalResponse> getById(@PathVariable Long id) {
        Proposal proposal = Optional.ofNullable(em.find(Proposal.class, id))
            .orElseThrow(() -> new NotFoundException("Proposal"));

        return ResponseEntity.ok(proposal.toDTO());
    }

    @Transactional
    @Scheduled(fixedDelay = 2000)
    public void createProposalCardTask() {
        int i = 0;

        while(i < proposalIds.size()) {
            Long id = proposalIds.get(i);

            try {
                Proposal proposal = em.find(Proposal.class, id);
                CardResponse cardResponse = cardClient.cards(proposal.toCard());

                proposal.setCard(cardResponse.toModel(em));
                proposalIds.remove(i);

                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
