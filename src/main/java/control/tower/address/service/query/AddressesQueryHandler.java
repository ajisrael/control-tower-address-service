package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

import static control.tower.address.service.core.constants.ExceptionMessages.ADDRESS_WITH_ID_DOES_NOT_EXIST;

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
        return addressRepository.findById(query.getAddressId()).orElseThrow(
                () -> new IllegalStateException(String.format(ADDRESS_WITH_ID_DOES_NOT_EXIST, query.getAddressId())));
    }
}
