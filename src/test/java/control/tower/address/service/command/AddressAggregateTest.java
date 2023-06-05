package control.tower.address.service.command;

import control.tower.address.service.core.events.AddressCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressAggregateTest {

    private String ADDRESS_ID = "addressId";
    private String USER_ID = "userId";
    private String STREET = "street";
    private String CITY = "city";
    private String STATE = "state";
    private String POSTAL_CODE = "postalCode";
    private String COUNTRY = "country";

    private FixtureConfiguration<AddressAggregate> fixture;

    @BeforeEach
    void setup() {
        fixture = new AggregateTestFixture<>(AddressAggregate.class);
    }

    @Test
    void shouldCreateProductAggregate() {
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
}
