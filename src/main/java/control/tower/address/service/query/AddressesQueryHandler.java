package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import control.tower.address.service.query.querymodels.AddressQueryModel;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static control.tower.address.service.core.constants.ExceptionMessages.ADDRESS_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class AddressesQueryHandler {

    private final AddressRepository addressRepository;

    @QueryHandler
    public List<AddressQueryModel> findAllAddresses(FindAllAddressesQuery query) {
        List<AddressEntity> addressEntities = addressRepository.findAll();
        return convertAddressEntitiesToAddressQueryModels(addressEntities);
    }

    @QueryHandler
    public AddressQueryModel findAddress(FindAddressQuery query) {
        AddressEntity addressEntity = addressRepository.findById(query.getAddressId()).orElseThrow(
                () -> new IllegalStateException(String.format(ADDRESS_WITH_ID_DOES_NOT_EXIST, query.getAddressId())));

        return convertAddresEntityToAddressQueryModel(addressEntity);
    }

    private List<AddressQueryModel> convertAddressEntitiesToAddressQueryModels(
            List<AddressEntity> addressEntities) {
        List<AddressQueryModel> addressQueryModels = new ArrayList<>();

        for (AddressEntity addressEntity : addressEntities) {
            addressQueryModels.add(convertAddresEntityToAddressQueryModel(addressEntity));
        }

        return addressQueryModels;
    }

    private AddressQueryModel convertAddresEntityToAddressQueryModel(AddressEntity addressEntity) {
        return new AddressQueryModel(
                addressEntity.getAddressId(),
                addressEntity.getUserId(),
                addressEntity.getStreet(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.getPostalCode(),
                addressEntity.getCountry()
        );
    }

}
