package br.com.zup.Credicard.status;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "status", url = "${status.api}")
public interface StatusClient {

    @PostMapping("/api/solicitacao")
    StatusResponse status(StatusRequest request);
}
