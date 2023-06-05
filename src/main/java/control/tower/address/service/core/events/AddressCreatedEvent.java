package control.tower.address.service.core.events;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class AddressCreatedEvent {

    private String addressId;
    private String userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
