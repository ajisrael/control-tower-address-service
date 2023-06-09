package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

@Component
@AllArgsConstructor
public class AddressesQueryHandler {

    private final AddressRepository addressRepository;

    @QueryHandler
    public List<AddressEntity> findAllAddresses(FindAllAddressesQuery query) {
        return addressRepository.findAll();
    }

    @QueryHandler
    public AddressEntity findAddress(FindAddressQuery query) {
        AddressEntity addressEntity = addressRepository.findByAddressId(query.getAddressId());

        throwErrorIfEntityDoesNotExist(addressEntity,
                String.format("Address %s does not exist", query.getAddressId()));

        return addressEntity;
    }
}
