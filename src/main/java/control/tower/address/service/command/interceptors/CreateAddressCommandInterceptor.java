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

import static control.tower.address.service.core.utils.AddressHasher.createAddressHash;

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
                LOGGER.info(String.format("Intercepted command: %s", command.getPayloadType()));

                CreateAddressCommand createAddressCommand = (CreateAddressCommand) command.getPayload();

                AddressLookupEntity addressLookupEntity = addressLookupRepository.findByAddressId(
                        createAddressCommand.getAddressId());

                if (addressLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Address with id %s already exists",
                                    createAddressCommand.getAddressId())
                    );
                }

                addressLookupEntity = addressLookupRepository.findByAddressHash(createAddressHash(createAddressCommand));

                if (addressLookupEntity != null) {
                    throw new IllegalStateException("This address already exists for this user");
                }
            }

            return command;
        };
    }
}
