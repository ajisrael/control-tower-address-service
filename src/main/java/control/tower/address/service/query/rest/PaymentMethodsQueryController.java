package control.tower.address.service.query.rest;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.query.FindAllAddressesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class PaymentMethodsQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<AddressRestModel> getAddresses() {
        FindAllAddressesQuery findAllAddressesQuery = new FindAllAddressesQuery();

        List<AddressEntity> addressEntities = queryGateway.query(findAllAddressesQuery,
                ResponseTypes.multipleInstancesOf(AddressEntity.class)).join();

        return convertAddressEntitiesToAddressRestModels(addressEntities);
    }

    private List<AddressRestModel> convertAddressEntitiesToAddressRestModels(
            List<AddressEntity> addressEntities) {
        List<AddressRestModel> addressRestModels = new ArrayList<>();

        for (AddressEntity addressEntity : addressEntities) {
            addressRestModels.add(new AddressRestModel(
                    addressEntity.getAddressId(),
                    addressEntity.getUserId(),
                    addressEntity.getStreet(),
                    addressEntity.getCity(),
                    addressEntity.getState(),
                    addressEntity.getPostalCode(),
                    addressEntity.getCountry()
            ));
        }

        return addressRestModels;
    }
}
