package br.com.zup.Credicard.proposal.address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddressDTO {
    @NotBlank
    private final String country;

    @NotBlank
    private final String state;

    @NotBlank
    private final String city;

    @NotBlank
    private final String street;

    @NotNull
    private final Integer number;

    public AddressDTO(
        String country,
        String state,
        String city,
        String street,
        Integer number
    ) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
        this.state = state;
    }

    public Address toModel() {
        return new Address(
            country,
            state,
            city,
            street,
            number
        );
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
