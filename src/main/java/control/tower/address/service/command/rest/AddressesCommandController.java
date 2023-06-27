package control.tower.address.service.command.rest;

import control.tower.address.service.command.commands.CreateAddressCommand;
import control.tower.core.commands.RemoveAddressCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressesCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createAddress(@Valid @RequestBody CreateAddressRestModel createAddressRestModel) {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(UUID.randomUUID().toString())
                .userId(createAddressRestModel.getUserId())
                .street(createAddressRestModel.getStreet())
                .city(createAddressRestModel.getCity())
                .state(createAddressRestModel.getState())
                .postalCode(createAddressRestModel.getPostalCode())
                .country(createAddressRestModel.getCountry())
                .build();

        return commandGateway.sendAndWait(createAddressCommand);
    }

    @DeleteMapping
    public String removeAddress(@Valid @RequestBody RemoveAddressRestModel removeAddressRestModel) {
        RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                .addressId(removeAddressRestModel.getAddressId())
                .build();

        return commandGateway.sendAndWait(removeAddressCommand);
    }
}


