package control.tower.address.service.command.rest.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateAddressRestModel {

    @NotBlank(message = "userId is a required field")
    private String userId;
    @NotBlank(message = "street is a required field")
    private String street;
    @NotBlank(message = "city is a required field")
    private String city;
    @NotBlank(message = "state is a required field")
    private String state;
    @NotBlank(message = "postalCode is a required field")
    private String postalCode;
    @NotBlank(message = "country is a required field")
    private String country;
}
