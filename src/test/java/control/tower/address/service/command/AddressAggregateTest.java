package control.tower.address.service.command;

import control.tower.address.service.command.commands.CreateAddressCommand;
import control.tower.core.commands.RemoveAddressCommand;
import control.tower.address.service.core.events.AddressCreatedEvent;
import control.tower.address.service.core.events.AddressRemovedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressAggregateTest {

    private final String ADDRESS_ID = "addressId";
    private final String USER_ID = "userId";
    private final String STREET = "street";
    private final String CITY = "city";
    private final String STATE = "state";
    private final String POSTAL_CODE = "postalCode";
    private final String COUNTRY = "country";

    private FixtureConfiguration<AddressAggregate> fixture;

    @BeforeEach
    void setup() {
        fixture = new AggregateTestFixture<>(AddressAggregate.class);
    }

    @Test
    void shouldCreateAddressAggregate() {
        fixture.givenNoPriorActivity()
                .when(
                        CreateAddressCommand.builder()
                                .addressId(ADDRESS_ID)
                                .userId(USER_ID)
                                .street(STREET)
                                .city(CITY)
                                .state(STATE)
                                .postalCode(POSTAL_CODE)
                                .country(COUNTRY)
                                .build())
                .expectEvents(
                        AddressCreatedEvent.builder()
                                .addressId(ADDRESS_ID)
                                .userId(USER_ID)
                                .street(STREET)
                                .city(CITY)
                                .state(STATE)
                                .postalCode(POSTAL_CODE)
                                .country(COUNTRY)
                                .build())
                .expectState(
                        addressAggregate -> {
                            assertEquals(ADDRESS_ID, addressAggregate.getAddressId());
                            assertEquals(USER_ID, addressAggregate.getUserId());
                            assertEquals(STREET, addressAggregate.getStreet());
                            assertEquals(CITY, addressAggregate.getCity());
                            assertEquals(STATE, addressAggregate.getState());
                            assertEquals(POSTAL_CODE, addressAggregate.getPostalCode());
                            assertEquals(COUNTRY, addressAggregate.getCountry());
                        }
                );
    }

    @Test
    void shouldRemoveAddressAggregate() {
        AddressCreatedEvent addressCreatedEvent = AddressCreatedEvent.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .build();

        AddressRemovedEvent addressRemovedEvent = AddressRemovedEvent.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .build();

        fixture.given(addressCreatedEvent)
                .when(removeAddressCommand)
                .expectEvents(addressRemovedEvent);
    }
}
