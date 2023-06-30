package control.tower.address.service.core.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<AddressEntity, String> {

    AddressEntity findByAddressId(String addressId);

    List<AddressEntity> findByUserId(String userId);

    Page<AddressEntity> findByUserId(String userId, Pageable pageable);
}
