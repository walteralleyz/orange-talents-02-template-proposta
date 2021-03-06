package br.com.zup.Credicard.card;

import br.com.zup.Credicard.advice.AdviceRequest;
import br.com.zup.Credicard.advice.AdviceResponse;
import br.com.zup.Credicard.blocking.BlockingRequest;
import br.com.zup.Credicard.blocking.BlockingResponse;
import br.com.zup.Credicard.wallet.WalletRequest;
import br.com.zup.Credicard.wallet.WalletResponse;
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

    @PostMapping("/api/cartoes/{id}/avisos")
    AdviceResponse travel(@PathVariable String id, AdviceRequest request);

    @PostMapping("/api/cartoes/{id}/carteiras")
    WalletResponse wallet(@PathVariable String id, WalletRequest request);

}
