package control.tower.product.service.core.data;

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
@Table(name = "paymentmethodlookup")
public class PaymentMethodLookupEntity implements Serializable {

    private static final long serialVersionUID = -4787108556148621716L;

    @Id
    @Column(unique = true)
    private String paymentId;
    @Column(unique = true)
    private String cardNumber;
}
