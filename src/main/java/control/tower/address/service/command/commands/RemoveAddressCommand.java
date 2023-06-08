package control.tower.address.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.isNullOrBlank;

@Getter
@Builder
public class RemoveAddressCommand {

    @TargetAggregateIdentifier
    private String addressId;

    public void validate() {
        if (isNullOrBlank(this.getAddressId())) {
            throw new IllegalArgumentException("Address id cannot be empty");
        }
    }
}
