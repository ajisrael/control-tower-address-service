package control.tower.address.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.isNullOrBlank;

@Getter
@Builder
public class CreateAddressCommand {

    @TargetAggregateIdentifier
    private String addressId;
    private String userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public void validate() {
        if (isNullOrBlank(this.getAddressId())) {
            throw new IllegalArgumentException("Address id cannot be empty");
        }

        if (isNullOrBlank(this.getUserId())) {
            throw new IllegalArgumentException("User id cannot be empty");
        }

        if (isNullOrBlank(this.getStreet())) {
            throw new IllegalArgumentException("Street cannot be empty");
        }

        if (isNullOrBlank(this.getCity())) {
            throw new IllegalArgumentException("City cannot be empty");
        }

        if (isNullOrBlank(this.getState())) {
            throw new IllegalArgumentException("State cannot be empty");
        }

        if (isNullOrBlank(this.getPostalCode())) {
            throw new IllegalArgumentException("Postal code cannot be empty");
        }

        if (isNullOrBlank(this.getCountry())) {
            throw new IllegalArgumentException("Country cannot be empty");
        }
    }
}
