package control.tower.address.service.command;

import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.events.AddressCreatedEvent;
import control.tower.address.service.core.data.AddressLookupRepository;
import control.tower.address.service.core.events.AddressRemovedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static control.tower.address.service.core.constants.ExceptionMessages.ADDRESS_LOOKUP_ENTITY_WITH_ID_DOES_NOT_EXIST;
import static control.tower.address.service.core.utils.AddressHasher.*;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;

@Component
@AllArgsConstructor
@ProcessingGroup("address-group")
public class AddressLookupEventsHandler {

    private AddressLookupRepository addressLookupRepository;

    @EventHandler
    public void on(AddressCreatedEvent event) {
        addressLookupRepository.save(new AddressLookupEntity(event.getAddressId(), createAddressHash(event)));
    }

    @EventHandler
    public void on(AddressRemovedEvent event) {
        AddressLookupEntity addressLookupEntity = addressLookupRepository.findByAddressId(event.getAddressId());
        throwExceptionIfEntityDoesNotExist(addressLookupEntity,
                String.format(ADDRESS_LOOKUP_ENTITY_WITH_ID_DOES_NOT_EXIST, event.getAddressId()));
        addressLookupRepository.delete(addressLookupEntity);
    }
}
