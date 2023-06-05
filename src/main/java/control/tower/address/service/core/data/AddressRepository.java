package control.tower.address.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, String> {

    AddressEntity findByAddressId(String addressId);
}
