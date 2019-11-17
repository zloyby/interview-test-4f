package cz.finance.hr.test.core.util;

import cz.finance.hr.test.exception.CommonException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

    static String longToStringIp(Long ip) {
        return String.format("%d.%d.%d.%d", (ip >> 24 & 0xff), (ip >> 16 & 0xff), (ip >> 8 & 0xff), (ip & 0xff));
    }

    public static InetAddress longToInetAddress(Long ip) {
        try {
            return InetAddress.getByName(longToStringIp(ip));
        } catch (UnknownHostException e) {
            throw new CommonException("Invalid IP number " + ip);
        }
    }
}
