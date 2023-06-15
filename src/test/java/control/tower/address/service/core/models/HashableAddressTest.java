package control.tower.address.service.core.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashableAddressTest {

    private final String USER_ID = "userId";
    private final String STREET = "street";
    private final String CITY = "city";
    private final String STATE = "state";
    private final String POSTAL_CODE = "postalCode";
    private final String COUNTRY = "country";

    @Test
    void shouldGetCorrectCombinedValues() {
        HashableAddress hashableAddress = new HashableAddress();
        hashableAddress.setUserId(USER_ID);
        hashableAddress.setStreet(STREET);
        hashableAddress.setCity(CITY);
        hashableAddress.setState(STATE);
        hashableAddress.setPostalCode(POSTAL_CODE);
        hashableAddress.setCountry(COUNTRY);

        String combinedValues = USER_ID + STREET + CITY + STATE + POSTAL_CODE + COUNTRY;

        assertEquals(hashableAddress.getCombinedValues(), combinedValues);
    }
}
