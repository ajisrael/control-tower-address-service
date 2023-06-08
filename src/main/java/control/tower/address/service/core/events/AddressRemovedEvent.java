package control.tower.address.service.core.events;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressRemovedEvent {

    private String addressId;
    private String userId;
}
