package br.com.zup.Credicard.proposal;

import br.com.zup.Credicard.annotation.CPFOrCNPJ;
import br.com.zup.Credicard.annotation.Unique;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Base64;

@Validated
public class ProposalRequest {
    @NotBlank
    private final String name;

    @NotBlank
    @CPFOrCNPJ
    @Unique(domainClass = Proposal.class, fieldName = "doc")
    private final String doc;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    private final String address;

    @NotNull
    @Positive
    private final BigDecimal salary;

    public ProposalRequest(
        String doc,
        String name,
        String email,
        String address,
        BigDecimal salary
    ) {
        this.name = name;
        this.doc = doc;
        this.email = email;
        this.address = address;
        this.salary = salary;
    }

    public Proposal toModel() {
        return new Proposal(Base64.getEncoder().encodeToString(doc.getBytes()), name, email, address, salary);
    }

    public String getName() {
        return name;
    }

    public String getDoc() {
        return doc;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
