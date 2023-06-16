package control.tower.address.service.command;

import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.events.AddressCreatedEvent;
import control.tower.address.service.core.data.AddressLookupRepository;
import control.tower.address.service.core.events.AddressRemovedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static control.tower.address.service.core.utils.AddressHasher.createAddressHash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressLookupEventsHandlerTest {

    private AddressLookupEventsHandler addressLookupEventsHandler;

    @Mock
    private AddressLookupRepository addressLookupRepository;

    private final String ADDRESS_ID = "addressId";
    private final String USER_ID = "userId";
    private final String STREET = "street";
    private final String CITY = "city";
    private final String STATE = "state";
    private final String POSTAL_CODE = "postalCode";
    private final String COUNTRY = "country";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        addressLookupEventsHandler = new AddressLookupEventsHandler(addressLookupRepository);
    }

    @Test
    void shouldSaveAddressLookupEntityOnAddressCreatedEvent() {
        // Arrange
        AddressCreatedEvent event = AddressCreatedEvent.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        // Act
        addressLookupEventsHandler.on(event);

        // Assert
        ArgumentCaptor<AddressLookupEntity> captor = ArgumentCaptor.forClass(AddressLookupEntity.class);
        Mockito.verify(addressLookupRepository).save(captor.capture());
        AddressLookupEntity savedEntity = captor.getValue();

        assertEquals(event.getAddressId(), savedEntity.getAddressId());
        assertEquals(createAddressHash(event), savedEntity.getAddressHash());
    }

    @Test
    void shouldDeleteAddressLookupEntityOnAddressRemovedEvent() {
        // Arrange
        AddressRemovedEvent event = AddressRemovedEvent.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .build();
        AddressLookupEntity addressLookupEntity = new AddressLookupEntity(ADDRESS_ID, "addressHash123");

        Mockito.when(addressLookupRepository.findByAddressId(ADDRESS_ID)).thenReturn(addressLookupEntity);

        // Act
        addressLookupEventsHandler.on(event);

        // Assert
        Mockito.verify(addressLookupRepository).delete(addressLookupEntity);
    }

    @Test
    void shouldThrowExceptionOnAddressRemovedEventForNonExistingAddress() {
        // Arrange
        AddressRemovedEvent event =  AddressRemovedEvent.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .build();

        Mockito.when(addressLookupRepository.findByAddressId(ADDRESS_ID)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> addressLookupEventsHandler.on(event));
    }
}

