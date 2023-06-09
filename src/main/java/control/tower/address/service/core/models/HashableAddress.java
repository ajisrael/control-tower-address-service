package control.tower.address.service.core.models;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class HashableAddress {

    private String userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public String getCombinedValues() {
        return userId + street + city + state + postalCode + country;
    }
}
