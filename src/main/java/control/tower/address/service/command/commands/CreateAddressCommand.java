package control.tower.address.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.constants.ExceptionMessages.ADDRESS_ID_CANNOT_BE_EMPTY;
import static control.tower.address.service.core.constants.ExceptionMessages.USER_ID_CANNOT_BE_EMPTY;
import static control.tower.address.service.core.constants.ExceptionMessages.STREET_CANNOT_BE_EMPTY;
import static control.tower.address.service.core.constants.ExceptionMessages.CITY_CANNOT_BE_EMPTY;
import static control.tower.address.service.core.constants.ExceptionMessages.STATE_CANNOT_BE_EMPTY;
import static control.tower.address.service.core.constants.ExceptionMessages.POSTAL_CODE_CANNOT_BE_EMPTY;
import static control.tower.address.service.core.constants.ExceptionMessages.COUNTRY_CANNOT_BE_EMPTY;
import static control.tower.core.utils.Helper.throwExceptionIfParameterIsEmpty;

@Getter
@Builder
public class CreateAddressCommand {

    @TargetAggregateIdentifier
    private String addressId;
    private String userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getAddressId(), ADDRESS_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getStreet(), STREET_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getCity(), CITY_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getState(), STATE_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getPostalCode(), POSTAL_CODE_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getCountry(), COUNTRY_CANNOT_BE_EMPTY);
    }
}
