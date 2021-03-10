package br.com.zup.Credicard.proposal;

import br.com.zup.Credicard.proposal.address.AddressDTO;
import br.com.zup.Credicard.util.Mvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProposalTest {
    private final MockMvc mvc;
    private final ObjectMapper mapper;
    private final AddressDTO dto;

    private Mvc mvcUtil;
    private URI uri;

    @Autowired
    public ProposalTest(MockMvc mvc, ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;

        this.dto = new AddressDTO(
            "brasil",
            "sc",
            "Camboriu",
            "r joao",
            395
        );
    }

    @BeforeEach
    public void setup() throws URISyntaxException {
        this.mvcUtil = new Mvc(mvc);
        this.uri = new URI("/api/proposal");
    }

    @Test
    void shouldCreateAProposalWithCard() throws Exception {
        ProposalRequest request = new ProposalRequest(
            "09547284916",
            "walter",
            "walterdasilvasantos@gmail.com",
            dto,
            new BigDecimal(3500)
        );

        mvcUtil.doPost(uri, mapper.writeValueAsString(request), 201);
    }

    @Test
    void shouldCreateAProposalAndRetrieveCard() throws Exception {
        ProposalRequest request = new ProposalRequest(
            "15192779030",
            "rogerio",
            "rogerio@mail.com",
            dto,
            new BigDecimal(4000)
        );

        mvcUtil.doPost(uri, mapper.writeValueAsString(request), 201);

        Thread.sleep(10000);

        String response = mvcUtil.doGet(new URI(uri.toString() + "/1"), 200);

        Assertions.assertTrue(response.contains("idProposta"));
    }

    @Test
    void shouldReturnNotFoundStatus() throws Exception {
        mvcUtil.doGet(new URI(uri + "/1"), 404);
    }
}
