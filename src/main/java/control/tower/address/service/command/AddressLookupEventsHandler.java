package control.tower.address.service.command;

import control.tower.address.service.core.data.AddressLookupEntity;
import control.tower.address.service.core.events.AddressCreatedEvent;
import control.tower.address.service.core.data.AddressLookupRepository;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@ProcessingGroup("address-group")
public class AddressLookupEventsHandler {

    private AddressLookupRepository addressLookupRepository;

    @EventHandler
    public void on(AddressCreatedEvent event) {
        addressLookupRepository.save(new AddressLookupEntity(event.getAddressId()));
    }
}
