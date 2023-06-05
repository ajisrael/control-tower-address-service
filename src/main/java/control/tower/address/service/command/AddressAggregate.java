package control.tower.address.service.command;

import control.tower.address.service.core.events.AddressCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static control.tower.address.service.core.utils.Helper.isNullOrBlank;

@Aggregate
@NoArgsConstructor
@Getter
public class AddressAggregate {

    @AggregateIdentifier
    private String addressId;
    private String userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @CommandHandler
    public AddressAggregate(CreateAddressCommand command) {
        validateCreateAddressCommand(command);

        AddressCreatedEvent event = AddressCreatedEvent.builder()
                .addressId(command.getAddressId())
                .userId(command.getUserId())
                .street(command.getStreet())
                .city(command.getCity())
                .state(command.getState())
                .postalCode(command.getPostalCode())
                .country(command.getCountry())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventHandler
    public void on(AddressCreatedEvent event) {
        this.addressId = event.getAddressId();
        this.userId = event.getUserId();
        this.street = event.getStreet();
        this.city = event.getCity();
        this.state = event.getState();
        this.postalCode = event.getPostalCode();
        this.country = event.getCountry();
    }

    private void validateCreateAddressCommand(CreateAddressCommand command) {
        if (isNullOrBlank(command.getAddressId())) {
            throw new IllegalArgumentException("Address id cannot be empty");
        }

        if (isNullOrBlank(command.getUserId())) {
            throw new IllegalArgumentException("User id cannot be empty");
        }

        if (isNullOrBlank(command.getStreet())) {
            throw new IllegalArgumentException("Street cannot be empty");
        }

        if (isNullOrBlank(command.getCity())) {
            throw new IllegalArgumentException("City cannot be empty");
        }

        if (isNullOrBlank(command.getState())) {
            throw new IllegalArgumentException("State cannot be empty");
        }

        if (isNullOrBlank(command.getPostalCode())) {
            throw new IllegalArgumentException("Postal code cannot be empty");
        }

        if (isNullOrBlank(command.getCountry())) {
            throw new IllegalArgumentException("Country cannot be empty");
        }
    }
}
