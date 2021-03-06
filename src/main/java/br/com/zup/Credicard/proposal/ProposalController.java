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

    List<Long> elected = new ArrayList<>();
    List<Long> persisted = new ArrayList<>();

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(
        @RequestBody @Valid ProposalRequest request,
        UriComponentsBuilder builder
    ) {
        Proposal proposal = request.toModel();
        URI uri;

        proposal = em.merge(proposal);
        uri = builder.path("/api/proposal/{id}").buildAndExpand(proposal.getId()).toUri();

        persisted.add(proposal.getId());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalResponse> getById(@PathVariable Long id) {
        Proposal proposal = Optional.ofNullable(em.find(Proposal.class, id))
            .orElseThrow(() -> new NotFoundException("Proposal"));

        return ResponseEntity.ok(proposal.toDTO());
    }

    @Transactional
    @Scheduled(fixedDelay = 2000)
    public void createProposalElectedTask() {
        while(!persisted.isEmpty()) {
            Long id = persisted.get(0);
            Proposal proposal = em.find(Proposal.class, id);

            try {
                StatusResponse response = statusClient.status(proposal.toStatus());
                proposal.setProposalStatus(response.getResultadoSolicitacao());

                if(proposal.isElegivel()) {
                    elected.add(proposal.getId());
                    persisted.remove(0);
                }
            } catch (Exception e) {
                proposal.setProposalStatus(StatusType.COM_RESTRICAO);
            }
        }
    }

    @Transactional
    @Scheduled(fixedDelay = 2000)
    public void createProposalCardTask() {
        while(!elected.isEmpty()) {
            Long id = elected.get(0);

            try {
                Proposal proposal = em.find(Proposal.class, id);
                CardResponse cardResponse = cardClient.cards(proposal.toCard());

                proposal.setCard(cardResponse.toModel(em));
                elected.remove(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
