package control.tower.address.service.command.interceptors;

import control.tower.address.service.command.commands.CreateAddressCommand;
import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.data.AddressLookupRepository;
import control.tower.address.service.core.utils.WebClientService;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.address.service.core.utils.AddressHasher.createAddressHash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class CreateAddressCommandInterceptorTest {

    private CreateAddressCommandInterceptor interceptor;
    private AddressLookupRepository lookupRepository;

    private final String ADDRESS_ID = "addressId";
    private final String USER_ID = "userId";
    private final String STREET = "street";
    private final String CITY = "city";
    private final String STATE = "state";
    private final String POSTAL_CODE = "postalCode";
    private final String COUNTRY = "country";

    @BeforeEach
    void setUp() {
        lookupRepository = mock(AddressLookupRepository.class);
        interceptor = new CreateAddressCommandInterceptor(lookupRepository);
    }

    @Test
    void shouldProcessValidCreateAddressCommand() {
        CreateAddressCommand validCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        CommandMessage<CreateAddressCommand> commandMessage = new GenericCommandMessage<>(validCommand);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        CommandMessage<?> processedCommand = result.apply(0, commandMessage);

        assertEquals(commandMessage, processedCommand);
    }

    @Test
    void shouldThrowExceptionWhenProcessingExistingAddressId() {
        CreateAddressCommand existingAddressIdCommand = CreateAddressCommand.builder()
                .addressId(ADDRESS_ID)
                .userId(USER_ID)
                .street("newStreet")
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        CommandMessage<CreateAddressCommand> commandMessage = new GenericCommandMessage<>(existingAddressIdCommand);

        AddressLookupEntity existingEntity = new AddressLookupEntity(ADDRESS_ID, createAddressHash(existingAddressIdCommand));
        when(lookupRepository.findByAddressId(ADDRESS_ID)).thenReturn(existingEntity);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        assertThrows(IllegalArgumentException.class, () -> result.apply(0, commandMessage));

        verify(lookupRepository).findByAddressId(ADDRESS_ID);
    }

    @Test
    void shouldThrowExceptionWhenProcessingExistingAddressForUser() {
        String newAddressId = "newAddressId";
        CreateAddressCommand existingAddressForUserCommand = CreateAddressCommand.builder()
                .addressId(newAddressId)
                .userId(USER_ID)
                .street(STREET)
                .city(CITY)
                .state(STATE)
                .postalCode(POSTAL_CODE)
                .country(COUNTRY)
                .build();

        CommandMessage<CreateAddressCommand> commandMessage = new GenericCommandMessage<>(existingAddressForUserCommand);

        String existingAddressForUserHash = createAddressHash(existingAddressForUserCommand);

        AddressLookupEntity existingEntity = new AddressLookupEntity(newAddressId, existingAddressForUserHash);
        when(lookupRepository.findByAddressHash(existingAddressForUserHash)).thenReturn(existingEntity);

        BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> result = interceptor.handle(List.of(commandMessage));

        assertThrows(IllegalArgumentException.class, () -> result.apply(0, commandMessage));

        verify(lookupRepository).findByAddressHash(existingAddressForUserHash);
    }
}
