package control.tower.address.service.command.interceptors;

import control.tower.address.service.command.commands.RemoveAddressCommand;
import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.data.AddressLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.address.service.core.utils.AddressHasher.createAddressHash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RemoveAddressCommandInterceptorTest {

    private RemoveAddressCommandInterceptor interceptor;
    private AddressLookupRepository lookupRepository;

    private final String ADDRESS_ID = "addressId";

    @BeforeEach
    void setUp() {
        lookupRepository = mock(AddressLookupRepository.class);
        interceptor = new RemoveAddressCommandInterceptor(lookupRepository);
    }

    @Test
    void shouldProcessValidCommand() {
        RemoveAddressCommand validCommand = RemoveAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .build();

        CommandMessage<RemoveAddressCommand> commandMessage = new GenericCommandMessage<>(validCommand);

        AddressLookupEntity addressEntity = new AddressLookupEntity(ADDRESS_ID, createAddressHash(validCommand));
        when(lookupRepository.findByAddressId(ADDRESS_ID)).thenReturn(addressEntity);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        CommandMessage<?> processedCommand = result.apply(0, commandMessage);

        assertEquals(commandMessage, processedCommand);

        verify(lookupRepository).findByAddressId(ADDRESS_ID);
    }

    @Test
    void shouldThrowExceptionWhenProcessingNonExistingAddressId() {
        RemoveAddressCommand nonExistingAddressIdCommand = RemoveAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .build();

        CommandMessage<RemoveAddressCommand> commandMessage = new GenericCommandMessage<>(nonExistingAddressIdCommand);

        when(lookupRepository.findByAddressId(ADDRESS_ID)).thenReturn(null);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        assertThrows(IllegalArgumentException.class, () -> result.apply(0, commandMessage));

        verify(lookupRepository).findByAddressId(ADDRESS_ID);
    }
}
