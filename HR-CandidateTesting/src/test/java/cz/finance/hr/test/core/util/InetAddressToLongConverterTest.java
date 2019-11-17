package cz.finance.hr.test.core.util;

import com.google.common.net.InetAddresses;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static cz.finance.hr.test.core.util.InetAddressToLongConverter.ipToLong;
import static cz.finance.hr.test.core.util.InetAddressToLongConverter.longToStringIp;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InetAddressToLongConverterTest {

    @Test
    public void ipToLongTest() {
        try {
            long from = ipToLong(InetAddress.getByName("192.200.0.0"));
            long to = ipToLong(InetAddress.getByName("192.255.0.0"));
            long find = ipToLong(InetAddress.getByName("192.200.3.0"));

            assertTrue(find >= from && find <= to);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Test
    public void ipToLongWithGuavaLibraryTest() {
        try {
            int from = InetAddresses.coerceToInteger(InetAddress.getByName("192.200.0.0"));
            int to = InetAddresses.coerceToInteger(InetAddress.getByName(("192.255.0.0")));
            int find = InetAddresses.coerceToInteger(InetAddress.getByName("192.200.3.0"));

            assertTrue(find >= from && find <= to);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void longToIpTest() {
        Map<Long, String> testValues = new HashMap<Long, String>() {{
            put(34614272L, "2.16.44.0");
            put(34628607L, "2.16.99.255");
            put(3653525247L, "217.196.94.255");
            put(1514158335L, "90.64.56.255");
            put(3653412352L, "217.194.166.0");
            put(3653575935L, "217.197.36.255");
        }};

        testValues.forEach((k, v) -> {
            assertEquals(v, longToStringIp(k));
        });
    }
}