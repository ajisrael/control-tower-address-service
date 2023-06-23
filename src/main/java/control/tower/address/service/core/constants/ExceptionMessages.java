package control.tower.address.service.core.constants;

import static control.tower.address.service.core.constants.DomainConstants.ADDRESS;
import static control.tower.address.service.core.constants.DomainConstants.ADDRESS_LOOKUP_ENTITY;
import static control.tower.core.constants.ExceptionMessages.*;

public class ExceptionMessages {

    private ExceptionMessages() {
        throw new IllegalStateException("Constants class");
    }

    public static final String USER_ID_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "userId");
    public static final String STREET_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "street");
    public static final String CITY_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "city");
    public static final String STATE_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "state");
    public static final String POSTAL_CODE_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "postalCode");
    public static final String COUNTRY_CANNOT_BE_EMPTY = String.format(PARAMETER_CANNOT_BE_EMPTY, "country");

    public static final String ADDRESS_LOOKUP_ENTITY_WITH_ID_DOES_NOT_EXIST = String.format(ENTITY_WITH_ID_DOES_NOT_EXIST, ADDRESS_LOOKUP_ENTITY, "%s");
    public static final String ADDRESS_WITH_ID_DOES_NOT_EXIST = String.format(ENTITY_WITH_ID_DOES_NOT_EXIST, ADDRESS, "%s");
    public static final String ADDRESS_WITH_ID_ALREADY_EXISTS = String.format(ENTITY_WITH_ID_ALREADY_EXISTS, ADDRESS, "%s");
    public static final String ADDRESS_ALREADY_EXISTS_FOR_USER = String.format(ENTITY_ALREADY_EXISTS_FOR_USER, ADDRESS);


}
