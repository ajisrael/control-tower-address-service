package control.tower.address.service.command.rest;

import control.tower.address.service.command.commands.CreateAddressCommand;
import control.tower.address.service.command.rest.responses.AddressCreatedRestModel;
import control.tower.address.service.command.rest.requests.CreateAddressRestModel;
import control.tower.core.commands.RemoveAddressCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressesCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AddressCreatedRestModel createAddress(@Valid @RequestBody CreateAddressRestModel createAddressRestModel) {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(UUID.randomUUID().toString())
                .userId(createAddressRestModel.getUserId())
                .street(createAddressRestModel.getStreet())
                .city(createAddressRestModel.getCity())
                .state(createAddressRestModel.getState())
                .postalCode(createAddressRestModel.getPostalCode())
                .country(createAddressRestModel.getCountry())
                .build();

        String addressId = commandGateway.sendAndWait(createAddressCommand);
        return AddressCreatedRestModel.builder().addressId(addressId).build();
    }

    @DeleteMapping(params = "addressId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddress(String addressId) {
        RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                .addressId(addressId)
                .build();

        commandGateway.sendAndWait(removeAddressCommand);
    }
}


