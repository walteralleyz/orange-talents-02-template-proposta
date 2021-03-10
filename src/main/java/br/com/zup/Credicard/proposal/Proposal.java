package br.com.zup.Credicard.proposal;

import br.com.zup.Credicard.card.Card;
import br.com.zup.Credicard.card.CardRequest;
import br.com.zup.Credicard.proposal.address.Address;
import br.com.zup.Credicard.status.StatusRequest;
import br.com.zup.Credicard.status.StatusType;
import br.com.zup.Credicard.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "proposals")
public class Proposal {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String doc;

    @Column(nullable = false)
    private String email;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Address address;

    @Column(nullable = false)
    private BigDecimal salary;

    @OneToOne
    @JoinColumn(name = "CARD_ID")
    private Card card;

    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;

    @Deprecated
    private Proposal() {}

    public Proposal(String doc, String name, String email, Address address, BigDecimal salary) {
        Validator validator = new Validator();

        setDoc(doc, validator);
        setName(name, validator);
        setEmail(email, validator);
        setAddress(address, validator);
        setSalary(salary, validator);

        validator.compile();
    }

    public ProposalResponse toDTO() {
        return new ProposalResponse(id, name, email, doc, address.toDTO(), salary, proposalStatus, card.toDTO());
    }

    public StatusRequest toStatus() {
        return new StatusRequest(id, doc, name);
    }

    public CardRequest toCard() {
        return new CardRequest(doc, name, id.toString());
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setProposalStatus(StatusType type) {
        if(type == StatusType.COM_RESTRICAO)
            this.proposalStatus = ProposalStatus.NAO_ELEGIVEL;
        else if(type == StatusType.SEM_RESTRICAO)
            this.proposalStatus = ProposalStatus.ELEGIVEL;
    }

    private void setDoc(String doc, Validator v) {
        if(v.body(doc).field("doc").notBlank().validate())
            this.doc = doc;
    }

    private void setName(String name, Validator v) {
        if(v.body(name).field("name").notBlank().validate())
            this.name = name;
    }

    private void setEmail(String email, Validator v) {
        if(v.body(email).field("email").notBlank().email().validate())
            this.email = email;
    }

    private void setAddress(Address address, Validator v) {
        if(v.body(address).field("address").notNull().validate())
            this.address = address;
    }

    private void setSalary(BigDecimal salary, Validator v) {
        if(v.body(salary).field("salary").notNull().min(new BigDecimal(1)).validate())
            this.salary = salary;
    }

    public boolean isElegivel() {
        return proposalStatus == ProposalStatus.ELEGIVEL;
    }

    public Long getId() {
        return id;
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

    public Address getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Card getCard() {
        return card;
    }

    public ProposalStatus getProposalStatus() {
        return proposalStatus;
    }
}
