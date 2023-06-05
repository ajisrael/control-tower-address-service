package control.tower.address.service.command.rest;

import control.tower.address.service.command.CreateAddressCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createPaymentMethod(@Valid @RequestBody CreateAddressRestModel createAddressRestModel) {
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
}


