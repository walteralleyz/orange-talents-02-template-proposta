package br.com.zup.Credicard.proposal.address;

import br.com.zup.Credicard.proposal.Proposal;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer number;

    @OneToMany(mappedBy = "address")
    private List<Proposal> proposals;

    @Deprecated
    private Address() {}

    public Address(String country, String state, String city, String street, Integer number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.state = state;
    }

    public AddressDTO toDTO() {
        return new AddressDTO(
            country,
            state,
            city,
            street,
            number
        );
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }
}
