package br.com.zup.Credicard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Value("${keycloak.auth-server-url}")
    private String url;

    @Value("${keycloak.resource}")
    private String client;

    @Value("${keycloak.credentials.secret}")
    private String secret;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> entity;

        request.setClient_id(client);
        request.setClient_secret(secret);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", request.getUsername());
        map.add("password", request.getPassword());
        map.add("grant_type", request.getGrant_type());
        map.add("client_id", request.getClient_id());
        map.add("client_secret", request.getClient_secret());

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<>(map, headers);

        ResponseEntity<LoginResponse> response = restTemplate.exchange(
            url + "/realms/usuarios/protocol/openid-connect/token",
            HttpMethod.POST,
            entity,
            LoginResponse.class
        );

        return ResponseEntity.ok().body(response.getBody());
    }
}
