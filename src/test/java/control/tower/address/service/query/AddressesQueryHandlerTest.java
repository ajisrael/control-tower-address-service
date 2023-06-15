package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class AddressesQueryHandlerTest {

    private AddressesQueryHandler queryHandler;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        queryHandler = new AddressesQueryHandler(addressRepository);
    }

    private void populateAddress(AddressEntity addressEntity, String addressId, String userId, String street, String city, String state, String postalCode, String country) {
        addressEntity.setAddressId(addressId);
        addressEntity.setUserId(userId);
        addressEntity.setStreet(street);
        addressEntity.setCity(city);
        addressEntity.setState(state);
        addressEntity.setPostalCode(postalCode);
        addressEntity.setCountry(country);
    }

    @Test
    void shouldHandleFindAllAddressesQueryAndReturnAllAddresses() {
        // Arrange
        AddressEntity address1 = new AddressEntity();
        populateAddress(address1, "address1", "user1", "123 Main St", "New York", "NY", "12345", "USA");

        AddressEntity address2 = new AddressEntity();
        populateAddress(address2, "address2", "user2", "456 Elm St", "Los Angeles", "CA", "67890", "USA");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(address1);
        addresses.add(address2);

        Mockito.when(addressRepository.findAll()).thenReturn(addresses);

        FindAllAddressesQuery query = new FindAllAddressesQuery();

        // Act
        List<AddressEntity> result = queryHandler.findAllAddresses(query);

        // Assert
        Assertions.assertEquals(addresses.size(), result.size());
        Assertions.assertEquals(addresses.get(0), result.get(0));
        Assertions.assertEquals(addresses.get(1), result.get(1));
    }

    @Test
    void shouldHandleFindAddressQueryForExistingAddressIdAndReturnAddressEntity() {
        // Arrange
        AddressEntity address = new AddressEntity();
        populateAddress(address, "addressId", "user1", "123 Main St", "New York", "NY", "12345", "USA");

        Mockito.when(addressRepository.findById(address.getAddressId())).thenReturn(Optional.of(address));

        FindAddressQuery query = new FindAddressQuery(address.getAddressId());

        // Act
        AddressEntity result = queryHandler.findAddress(query);

        // Assert
        Assertions.assertEquals(address, result);
    }

    @Test
    void shouldHandleFindAddressQueryForNonExistingAddressIdAndThrowException() {
        // Arrange
        String addressId = "nonExistingAddressId";

        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        FindAddressQuery query = new FindAddressQuery(addressId);

        // Act & Assert
        Assertions.assertThrows(IllegalStateException.class, () -> queryHandler.findAddress(query));
    }
}

