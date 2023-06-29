package control.tower.address.service.query.rest;

import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.core.query.queries.FindAllAddressesForUserQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import control.tower.core.query.querymodels.AddressQueryModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Address Query API")
public class AddressesQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping()
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all addresses")
    public List<AddressQueryModel> getAddresses() {
        return queryGateway.query(new FindAllAddressesQuery(),
                ResponseTypes.multipleInstancesOf(AddressQueryModel.class)).join();
    }

    @GetMapping(params = "addressId", path = "/id")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get address by id")
    public AddressQueryModel getAddress(String addressId) {
        return queryGateway.query(new FindAddressQuery(addressId),
                ResponseTypes.instanceOf(AddressQueryModel.class)).join();
    }

    @GetMapping(params = "userId", path = "/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all addresses for user by user id")
    public List<AddressQueryModel> getAddressesForUser(String userId) {
        return queryGateway.query(new FindAllAddressesForUserQuery(userId),
                ResponseTypes.multipleInstancesOf(AddressQueryModel.class)).join();
    }
}
