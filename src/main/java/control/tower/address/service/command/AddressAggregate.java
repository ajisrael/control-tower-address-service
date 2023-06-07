package control.tower.address.service.command;

import control.tower.address.service.command.commands.CreateAddressCommand;
import control.tower.address.service.core.events.AddressCreatedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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
        command.validate();

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
}
