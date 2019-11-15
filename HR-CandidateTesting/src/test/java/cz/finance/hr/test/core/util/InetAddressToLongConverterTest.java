package cz.finance.hr.test.core.util;

import com.google.common.net.InetAddresses;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.Test;
import static cz.finance.hr.test.core.util.InetAddressToLongConverter.ipToLong;
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


}