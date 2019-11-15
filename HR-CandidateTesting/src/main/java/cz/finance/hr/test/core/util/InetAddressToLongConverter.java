package cz.finance.hr.test.core.util;

import java.net.InetAddress;

@SuppressWarnings("WeakerAccess")
public class InetAddressToLongConverter {

    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }
}
