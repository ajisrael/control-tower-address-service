package control.tower.address.service.core.utils;

import control.tower.address.service.core.models.HashableAddress;
import org.springframework.beans.BeanUtils;

import static control.tower.core.utils.Helper.calculateSHA256Hash;

public class AddressHasher {
    public static String createAddressHash(Object address) {
        HashableAddress hashableAddress = new HashableAddress();
        BeanUtils.copyProperties(address, hashableAddress);
        return calculateSHA256Hash(hashableAddress.getCombinedValues());
    }
}

