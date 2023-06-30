package control.tower.address.service.query;

import control.tower.address.service.core.data.AddressEntity;
import control.tower.address.service.core.data.AddressRepository;
import control.tower.address.service.query.queries.FindAddressQuery;
import control.tower.core.query.queries.DoesAddressExistForUserQuery;
import control.tower.core.query.queries.FindAllAddressesForUserQuery;
import control.tower.address.service.query.queries.FindAllAddressesQuery;
import control.tower.core.query.querymodels.AddressQueryModel;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static control.tower.address.service.core.constants.ExceptionMessages.*;

@Component
@AllArgsConstructor
public class AddressesQueryHandler {

    private final AddressRepository addressRepository;

    @QueryHandler
    public Page<AddressQueryModel> findAllAddresses(FindAllAddressesQuery query) {
        return addressRepository.findAll(query.getPageable()).map(this::convertAddresEntityToAddressQueryModel);
    }

    @QueryHandler
    public AddressQueryModel findAddress(FindAddressQuery query) {
        AddressEntity addressEntity = addressRepository.findById(query.getAddressId()).orElseThrow(
                () -> new IllegalStateException(String.format(ADDRESS_WITH_ID_DOES_NOT_EXIST, query.getAddressId())));

        return convertAddresEntityToAddressQueryModel(addressEntity);
    }

    @QueryHandler
    public List<AddressQueryModel> findAllAddressesForUser(FindAllAddressesForUserQuery query) {
        List<AddressEntity> addressEntities = addressRepository.findByUserId(query.getUserId());

        if (addressEntities.isEmpty()) {
            throw new IllegalArgumentException(String.format(NO_ADDRESSES_FOUND_FOR_USER, query.getUserId()));
        }

        return convertAddressEntitiesToAddressQueryModels(addressEntities);
    }

    @QueryHandler
    public boolean doesAddressExistForUser(DoesAddressExistForUserQuery query) {
        AddressEntity addressEntity = addressRepository.findById(query.getAddressId()).orElseThrow(
                () -> new IllegalArgumentException(String.format(
                        ADDRESS_ENTITY_WITH_ID_DOES_NOT_EXIST, query.getAddressId())));

        return addressEntity.getUserId().equals(query.getUserId());
    }

    private List<AddressQueryModel> convertAddressEntitiesToAddressQueryModels(List<AddressEntity> addressEntities) {
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
