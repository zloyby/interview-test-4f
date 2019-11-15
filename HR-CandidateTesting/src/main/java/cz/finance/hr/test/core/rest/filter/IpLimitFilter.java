package cz.finance.hr.test.core.rest.filter;

import com.google.common.net.InetAddresses;
import cz.finance.hr.test.core.util.InetAddressToLongConverter;
import java.io.IOException;
import java.net.InetAddress;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class IpLimitFilter implements Filter {

    /**
     * Limit to:
     * X requests from one city per TIME
     * Y requests from one region per TIME
     * Z requests from one country per TIME
     */
    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String remoteAddr = req.getRemoteAddr();
        InetAddress inetAddress = InetAddresses.forString(remoteAddr);
        Long ipNumber = InetAddressToLongConverter.ipToLong(inetAddress);
        //TODO: save to DB\cache with links to [city,region,country]
        log.info("IP: {}; {}; {}", remoteAddr, inetAddress, ipNumber);

        //TODO: check IP from DB\cache for has too many requests per last hour
        boolean b = false;
        if (b) {
            res.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too Many Requests");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
