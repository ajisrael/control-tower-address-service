package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AddressesQueryHandler {

    private final AddressRepository addressRepository;

    @QueryHandler
    public List<AddressEntity> findAllAddresses(FindAllAddressesQuery query) {
        return addressRepository.findAll();
    }
}
