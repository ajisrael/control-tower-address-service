package control.tower.address.service.command.interceptors;

import control.tower.address.service.command.commands.CreateAddressCommand;
import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.data.AddressLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.address.service.core.constants.ExceptionMessages.*;
import static control.tower.address.service.core.utils.AddressHasher.createAddressHash;
import static control.tower.core.utils.WebClientService.doesUserExist;
import static control.tower.core.constants.LogMessages.INTERCEPTED_COMMAND;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesExist;

@Component
public class CreateAddressCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAddressCommandInterceptor.class);

    private final AddressLookupRepository addressLookupRepository;

    public CreateAddressCommandInterceptor(AddressLookupRepository addressLookupRepository) {
        this.addressLookupRepository = addressLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            if (CreateAddressCommand.class.equals(command.getPayloadType())) {
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                CreateAddressCommand createAddressCommand = (CreateAddressCommand) command.getPayload();

                createAddressCommand.validate();

                boolean userDoesExist = doesUserExist(createAddressCommand.getUserId());

                if (!userDoesExist){
                    throw new IllegalArgumentException(
                            String.format(USER_WIth_ID_DOES_NOT_EXIST_CANNOT_CREATE_ADDRESS,
                                    createAddressCommand.getUserId()));
                }

                AddressLookupEntity addressLookupEntity = addressLookupRepository.findByAddressId(
                        createAddressCommand.getAddressId());

                throwExceptionIfEntityDoesExist(addressLookupEntity,
                        String.format(ADDRESS_WITH_ID_ALREADY_EXISTS, createAddressCommand.getAddressId()));

                addressLookupEntity = addressLookupRepository.findByAddressHash(createAddressHash(createAddressCommand));

                throwExceptionIfEntityDoesExist(addressLookupEntity, ADDRESS_ALREADY_EXISTS_FOR_USER);
            }

            return command;
        };
    }
}
