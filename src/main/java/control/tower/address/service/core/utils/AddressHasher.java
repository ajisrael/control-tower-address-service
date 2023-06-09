package control.tower.address.service.core.utils;

import control.tower.address.service.core.models.HashableAddress;
import org.springframework.beans.BeanUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AddressHasher {
    public static String createAddressHash(Object address) {
        HashableAddress hashableAddress = new HashableAddress();
        BeanUtils.copyProperties(address, hashableAddress);
        return hashAddress(hashableAddress);
    }

    public static String hashAddress(HashableAddress address) {
        return calculateSHA256Hash(address.getCombinedValues());
    }

    private static String calculateSHA256Hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception accordingly
            e.printStackTrace();
            return null;
        }
    }
}

