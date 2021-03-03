package br.com.zup.Credicard.card;

import br.com.zup.Credicard.card.blocking.BlockingRequest;
import br.com.zup.Credicard.card.blocking.BlockingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards", url = "${card.api}")
public interface CardClient {

    @PostMapping("/api/cartoes")
    CardResponse cards(CardRequest request);

    @GetMapping("/api/cartoes/{id}")
    CardResponse cardById(@PathVariable String id);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BlockingResponse block(@PathVariable String id, BlockingRequest request);

}
