package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import control.tower.core.query.querymodels.AddressQueryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressesQueryHandlerTest {

    private AddressesQueryHandler queryHandler;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        queryHandler = new AddressesQueryHandler(addressRepository);
    }

    private void populateAddressEntity(AddressEntity addressEntity, String addressId, String userId, String street, String city, String state, String postalCode, String country) {
        addressEntity.setAddressId(addressId);
        addressEntity.setUserId(userId);
        addressEntity.setStreet(street);
        addressEntity.setCity(city);
        addressEntity.setState(state);
        addressEntity.setPostalCode(postalCode);
        addressEntity.setCountry(country);
    }

    private void compareAddressEntityWithAddressQueryModel(AddressEntity addressEntity, AddressQueryModel addressQueryModel) {
        assertEquals(addressEntity.getAddressId(), addressQueryModel.getAddressId());
        assertEquals(addressEntity.getUserId(), addressQueryModel.getUserId());
        assertEquals(addressEntity.getStreet(), addressQueryModel.getStreet());
        assertEquals(addressEntity.getCity(), addressQueryModel.getCity());
        assertEquals(addressEntity.getState(), addressQueryModel.getState());
        assertEquals(addressEntity.getPostalCode(), addressQueryModel.getPostalCode());
        assertEquals(addressEntity.getCountry(), addressQueryModel.getCountry());
    }

    @Test
    void shouldHandleFindAllAddressesQueryAndReturnAllAddresses() {
        // Arrange
        AddressEntity address1 = new AddressEntity();
        populateAddressEntity(address1, "address1", "user1", "123 Main St", "New York", "NY", "12345", "USA");

        AddressEntity address2 = new AddressEntity();
        populateAddressEntity(address2, "address2", "user2", "456 Elm St", "Los Angeles", "CA", "67890", "USA");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(address1);
        addresses.add(address2);

        Mockito.when(addressRepository.findAll()).thenReturn(addresses);

        FindAllAddressesQuery query = new FindAllAddressesQuery();

        // Act
        List<AddressQueryModel> result = queryHandler.findAllAddresses(query);

        // Assert
        assertEquals(addresses.size(), result.size());
        compareAddressEntityWithAddressQueryModel(addresses.get(0), result.get(0));
        compareAddressEntityWithAddressQueryModel(addresses.get(1), result.get(1));
    }

    @Test
    void shouldHandleFindAddressQueryForExistingAddressIdAndReturnAddressEntity() {
        // Arrange
        AddressEntity address = new AddressEntity();
        populateAddressEntity(address, "addressId", "user1", "123 Main St", "New York", "NY", "12345", "USA");

        Mockito.when(addressRepository.findById(address.getAddressId())).thenReturn(Optional.of(address));

        FindAddressQuery query = new FindAddressQuery(address.getAddressId());

        // Act
        AddressQueryModel result = queryHandler.findAddress(query);

        // Assert
        compareAddressEntityWithAddressQueryModel(address, result);
    }

    @Test
    void shouldHandleFindAddressQueryForNonExistingAddressIdAndThrowException() {
        // Arrange
        String addressId = "nonExistingAddressId";

        Mockito.when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        FindAddressQuery query = new FindAddressQuery(addressId);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> queryHandler.findAddress(query));
    }
}

