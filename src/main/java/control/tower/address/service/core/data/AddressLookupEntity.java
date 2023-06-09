package control.tower.address.service.core.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresslookup")
public class AddressLookupEntity implements Serializable {

    private static final long serialVersionUID = -4787108556148621717L;

    @Id
    @Column(unique = true)
    private String addressId;
    @Column(unique = true)
    private String addressHash;
}
