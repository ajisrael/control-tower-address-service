package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.core.events.AddressCreatedEvent;
import control.tower.address.service.core.events.AddressRemovedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class AddressEventsHandlerTest {

    private AddressEventsHandler eventsHandler;

    @Mock
    private AddressRepository addressRepository;

    private final String ADDRESS_ID = "addressId";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventsHandler = new AddressEventsHandler(addressRepository);
    }

    @Test
    void shouldSaveAddressEntityOnAddressCreatedEvent() {
        // Arrange
        AddressCreatedEvent event = AddressCreatedEvent.builder()
                .addressId(ADDRESS_ID)
                .userId("user1")
                .street("123 Main St")
                .city("New York")
                .state("NY")
                .postalCode("12345")
                .country("USA")
                .build();

        Mockito.when(addressRepository.save(any(AddressEntity.class))).thenAnswer(invocation -> {
            AddressEntity addressEntity = invocation.getArgument(0);
            addressEntity.setAddressId(ADDRESS_ID); // Set an ID to simulate saving in the repository
            return addressEntity;
        });

        // Act
        eventsHandler.on(event);

        // Assert
        Mockito.verify(addressRepository).save(any(AddressEntity.class));
    }

    @Test
    void shouldDeleteAddressEntityOnAddressRemovedEvent() {
        // Arrange
        AddressRemovedEvent event = AddressRemovedEvent.builder().addressId(ADDRESS_ID).build();

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressId(ADDRESS_ID);

        Mockito.when(addressRepository.findByAddressId(event.getAddressId())).thenReturn(addressEntity);

        // Act
        eventsHandler.on(event);

        // Assert
        Mockito.verify(addressRepository).delete(eq(addressEntity));
    }

    @Test
    void shouldThrowExceptionOnAddressRemovedEventForNonExistingAddressId() {
        // Arrange
        AddressRemovedEvent event = AddressRemovedEvent.builder().addressId("nonExistingAddressId").build();

        Mockito.when(addressRepository.findByAddressId(event.getAddressId())).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> eventsHandler.on(event));
    }

    @Test
    void shouldThrowExceptionWhenHandlingGenericException() {
        // Arrange
        Exception exception = new Exception("Test exception");

        // Act & Assert
        assertThrows(Exception.class, () -> eventsHandler.handle(exception));
    }

    @Test
    void shouldLogErrorMessageWhenHandlingIllegalArgumentException() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Test exception");

        // Act
        eventsHandler.handle(exception);

        // Assert
        // Verify that the error message is logged
        // You can use your preferred logging framework to verify the logs
    }
}
