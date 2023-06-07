package control.tower.address.service.command.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateAddressRestModel {

    @NotBlank(message = "User id is a required field")
    private String userId;
    @NotBlank(message = "Street is a required field")
    private String street;
    @NotBlank(message = "City is a required field")
    private String city;
    @NotBlank(message = "State is a required field")
    private String state;
    @NotBlank(message = "Postal code is a required field")
    private String postalCode;
    @NotBlank(message = "Country is a required field")
    private String country;
}
