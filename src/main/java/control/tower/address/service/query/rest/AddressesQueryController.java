package control.tower.address.service.query.rest;

import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.core.query.queries.FindAllAddressesForUserQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import control.tower.core.query.querymodels.AddressQueryModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressesQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<AddressQueryModel> getAddresses() {
        return queryGateway.query(new FindAllAddressesQuery(),
                ResponseTypes.multipleInstancesOf(AddressQueryModel.class)).join();
    }

    @GetMapping(params = "addressId")
    public AddressQueryModel getAddress(String addressId) {
        return queryGateway.query(new FindAddressQuery(addressId),
                ResponseTypes.instanceOf(AddressQueryModel.class)).join();
    }

    @GetMapping(params = "userId")
    public List<AddressQueryModel> getAddressesForUser(String userId) {
        return queryGateway.query(new FindAllAddressesForUserQuery(userId),
                ResponseTypes.multipleInstancesOf(AddressQueryModel.class)).join();
    }
}
