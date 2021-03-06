package br.com.zup.Credicard.proposal;

import br.com.zup.Credicard.card.CardResponse;
import br.com.zup.Credicard.proposal.address.AddressDTO;

import java.math.BigDecimal;

public class ProposalResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final AddressDTO address;
    private final BigDecimal salary;
    private final ProposalStatus status;
    private final CardResponse card;

    public ProposalResponse(
        Long id,
        String name,
        String email,
        AddressDTO address,
        BigDecimal salary,
        ProposalStatus status,
        CardResponse card
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.salary = salary;
        this.status = status;
        this.card = card;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public CardResponse getCard() {
        return card;
    }
}
