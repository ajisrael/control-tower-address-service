package control.tower.address.service.command.interceptors;

import control.tower.core.commands.RemoveAddressCommand;
import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.data.AddressLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.address.service.core.constants.ExceptionMessages.ADDRESS_WITH_ID_DOES_NOT_EXIST;
import static control.tower.core.constants.LogMessages.INTERCEPTED_COMMAND;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;

@Component
public class RemoveAddressCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveAddressCommandInterceptor.class);

    private final AddressLookupRepository addressLookupRepository;

    public RemoveAddressCommandInterceptor(AddressLookupRepository addressLookupRepository) {
        this.addressLookupRepository = addressLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            if (RemoveAddressCommand.class.equals(command.getPayloadType())) {
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                RemoveAddressCommand removeAddressCommand = (RemoveAddressCommand) command.getPayload();

                removeAddressCommand.validate();

                AddressLookupEntity addressLookupEntity = addressLookupRepository.findByAddressId(
                        removeAddressCommand.getAddressId());

                throwExceptionIfEntityDoesNotExist(addressLookupEntity,
                        String.format(ADDRESS_WITH_ID_DOES_NOT_EXIST, removeAddressCommand.getAddressId()));
            }

            return command;
        };
    }
}
