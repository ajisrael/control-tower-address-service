package control.tower.address.service.command.interceptors;

import control.tower.address.service.command.CreateAddressCommand;
import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.data.AddressLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class CreateAddressCommandInterceptorTest {

    private CreateAddressCommandInterceptor interceptor;
    private AddressLookupRepository lookupRepository;

    @BeforeEach
    void setUp() {
        lookupRepository = mock(AddressLookupRepository.class);
        interceptor = new CreateAddressCommandInterceptor(lookupRepository);
    }

    @Test
    void testHandle_ValidCommand() {
        CreateAddressCommand validCommand = CreateAddressCommand.builder()
                .addressId("addressId")
                .userId("userId")
                .street("street")
                .city("city")
                .state("state")
                .postalCode("postalCode")
                .country("country")
                .build();

        CommandMessage<CreateAddressCommand> commandMessage = new GenericCommandMessage<>(validCommand);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        CommandMessage<?> processedCommand = result.apply(0, commandMessage);

        assertEquals(commandMessage, processedCommand);
    }

    @Test
    void testHandle_DuplicateProductId_ThrowsException() throws ParseException {
        String addressId = "addressId";

        CreateAddressCommand duplicateCommand = CreateAddressCommand.builder()
                .addressId(addressId)
                .userId("userId")
                .street("street")
                .city("city")
                .state("state")
                .postalCode("postalCode")
                .country("country")
                .build();

        CommandMessage<CreateAddressCommand> commandMessage = new GenericCommandMessage<>(duplicateCommand);

        AddressLookupEntity existingEntity = new AddressLookupEntity(addressId);
        when(lookupRepository.findByAddressId(addressId)).thenReturn(existingEntity);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        assertThrows(IllegalStateException.class, () -> result.apply(0, commandMessage));

        verify(lookupRepository).findByAddressId(addressId);
    }
}
