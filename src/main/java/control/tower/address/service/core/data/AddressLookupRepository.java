package control.tower.address.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressLookupRepository extends JpaRepository<AddressLookupEntity, String> {

    AddressLookupEntity findByAddressId(String addressId);

    AddressLookupEntity findByAddressHash(String addressHash);
}
