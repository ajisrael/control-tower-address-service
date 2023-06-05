package control.tower.address.service.core.data;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="address")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 789654123987456324L;

    @Id
    @Column(unique = true)
    private String addressId;
    private String userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
