package control.tower.address.service.command.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RemoveAddressRestModel {

    @NotBlank(message = "Address id is required")
    private String addressId;
}
