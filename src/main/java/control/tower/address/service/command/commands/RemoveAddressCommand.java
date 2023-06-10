package control.tower.address.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.address.service.core.constants.ExceptionMessages.ADDRESS_ID_CANNOT_BE_EMPTY;
import static control.tower.core.utils.Helper.throwExceptionIfParameterIsEmpty;

@Getter
@Builder
public class RemoveAddressCommand {

    @TargetAggregateIdentifier
    private String addressId;

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getAddressId(), ADDRESS_ID_CANNOT_BE_EMPTY);
    }
}
