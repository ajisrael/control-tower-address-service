package control.tower.address.service.core.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserRestModel {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userRole;
}