package br.com.zup.Credicard.card;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards", url = "${card.api}")
public interface CardClient {

    @PostMapping("/api/cartoes")
    CardResponse cards(CardRequest request);

    @GetMapping("/api/cartoes?idProposta={id}")
    CardResponse cardById(Long id);

}
