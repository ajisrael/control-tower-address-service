package control.tower.address.service.command.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateAddressCommandTest {

    private final String ADDRESS_ID = "addressId";
    private final String USER_ID = "userId";
    private final String STREET = "street";
    private final String CITY = "city";
    private final String STATE = "state";
    private final String POSTAL_CODE = "postalCode";
    private final String COUNTRY = "country";

    @Test
    void shouldBuildValidCreateAddressCommand() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertDoesNotThrow(createAddressCommand::validate);

        assertEquals(createAddressCommand.getAddressId(), ADDRESS_ID);
        assertEquals(createAddressCommand.getUserId(), USER_ID);
        assertEquals(createAddressCommand.getStreet(), STREET);
        assertEquals(createAddressCommand.getCity(), CITY);
        assertEquals(createAddressCommand.getState(), STATE);
        assertEquals(createAddressCommand.getPostalCode(), POSTAL_CODE);
        assertEquals(createAddressCommand.getCountry(), COUNTRY);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenAddressIdIsNull() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(null)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenAddressIdIsEmpty() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId("")
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenUserIdIsNull() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(null)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenUserIdIsEmpty() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId("")
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenStreetIsNull() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(null)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenStreetIsEmpty() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street("")
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenCityIsNull() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(null)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenCityIsEmpty() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city("")
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenStateIsNull() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(null)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenStateIsEmpty() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state("")
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenPostalCodeIsNull() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(null)
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenPostalCodeIsEmpty() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode("")
                .country(COUNTRY)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenCountryIsNull() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(null)
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }

    @Test
    void shouldNotBuildValidCreateAddressCommandWhenCountryIsEmpty() {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country("")
                .build();

        assertThrows(IllegalArgumentException.class, createAddressCommand::validate);
    }
}
