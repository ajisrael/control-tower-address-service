package control.tower.address.service.command.rest;

import control.tower.address.service.command.commands.CreateAddressCommand;
import control.tower.address.service.command.rest.responses.AddressCreatedResponseModel;
import control.tower.address.service.command.rest.requests.CreateAddressRequestModel;
import control.tower.core.commands.RemoveAddressCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address Command API")
public class AddressesCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create address")
    public AddressCreatedResponseModel createAddress(@Valid @RequestBody CreateAddressRequestModel createAddressRequestModel) {
        CreateAddressCommand createAddressCommand = CreateAddressCommand.builder()
                .addressId(UUID.randomUUID().toString())
                .userId(createAddressRequestModel.getUserId())
                .street(createAddressRequestModel.getStreet())
                .city(createAddressRequestModel.getCity())
                .state(createAddressRequestModel.getState())
                .postalCode(createAddressRequestModel.getPostalCode())
                .country(createAddressRequestModel.getCountry())
                .build();

        String addressId = commandGateway.sendAndWait(createAddressCommand);
        return AddressCreatedResponseModel.builder().addressId(addressId).build();
    }

    @DeleteMapping(params = "addressId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove address")
    public void removeAddress(String addressId) {
        RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                .addressId(addressId)
                .build();

        commandGateway.sendAndWait(removeAddressCommand);
    }
}


