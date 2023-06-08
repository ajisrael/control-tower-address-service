package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.core.events.AddressCreatedEvent;
import control.tower.address.service.core.events.AddressRemovedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

@Component
@ProcessingGroup("address-group")
public class AddressEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressEventsHandler.class);

    private final AddressRepository addressRepository;

    public AddressEventsHandler(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        LOGGER.error(exception.getLocalizedMessage());
    }

    @EventHandler
    public void on(AddressCreatedEvent event) {
        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(event, addressEntity);
        addressRepository.save(addressEntity);
    }

    @EventHandler
    public void on(AddressRemovedEvent event) {
        AddressEntity addressEntity = addressRepository.findByAddressId(event.getAddressId());
        throwErrorIfEntityDoesNotExist(addressEntity, String.format("Address %s does not exist", event.getAddressId()));
        addressRepository.delete(addressEntity);
    }
}
