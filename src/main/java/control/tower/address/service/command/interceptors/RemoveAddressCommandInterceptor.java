package control.tower.address.service.command.interceptors;

import control.tower.address.service.command.commands.RemoveAddressCommand;
import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.data.AddressLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

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
                LOGGER.info(String.format("Intercepted command: %s", command.getPayloadType()));

                RemoveAddressCommand removeAddressCommand = (RemoveAddressCommand) command.getPayload();

                AddressLookupEntity addressLookupEntity = addressLookupRepository.findByAddressId(
                        removeAddressCommand.getAddressId());

                throwErrorIfEntityDoesNotExist(addressLookupEntity, String.format("Address with id %s does not exist",
                        removeAddressCommand.getAddressId()));
            }

            return command;
        };
    }
}
