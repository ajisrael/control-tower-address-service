package control.tower.address.service.command;

import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.events.AddressCreatedEvent;
import control.tower.address.service.core.data.AddressLookupRepository;
import control.tower.address.service.core.events.AddressRemovedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static control.tower.address.service.core.utils.AddressHasher.*;
import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

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
        throwErrorIfEntityDoesNotExist(addressLookupEntity, String.format("Address Lookup entity %s does not exist", event.getAddressId()));
        addressLookupRepository.delete(addressLookupEntity);
    }
}
