package control.tower.address.service.command.rest.responses;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class AddressCreatedRestModel implements Serializable {

    private String addressId;
}
