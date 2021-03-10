package br.com.zup.Credicard.proposal;

import br.com.zup.Credicard.annotation.CPFOrCNPJ;
import br.com.zup.Credicard.annotation.Unique;
import br.com.zup.Credicard.proposal.address.AddressDTO;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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

    @NotNull
    private final AddressDTO address;

    @NotNull
    @Positive
    private final BigDecimal salary;

    public ProposalRequest(
        String doc,
        String name,
        String email,
        AddressDTO address,
        BigDecimal salary
    ) {
        this.name = name;
        this.doc = doc;
        this.email = email;
        this.address = address;
        this.salary = salary;
    }

    public Proposal toModel() {
        return new Proposal(
            generateEncodedDoc(),
            name, email, address.toModel(), salary);
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

    public AddressDTO getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String generateEncodedDoc() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(doc.getBytes());
            return md.digest().toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
