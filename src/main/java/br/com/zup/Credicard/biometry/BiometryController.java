package br.com.zup.Credicard.biometry;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/biometry")
public class BiometryController {
    @PersistenceContext
    private EntityManager em;

    @PostMapping("/card/{id}")
    @Transactional
    public ResponseEntity<?> create(
        @RequestBody @Valid BiometryRequest request,
        @PathVariable String id,
        UriComponentsBuilder builder
    ) {
        request.setCard(id, em);
        Biometry biometry = request.toModel();

        em.persist(biometry);

        URI uri = builder.path("/api/biometry/{id}").buildAndExpand(biometry.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
