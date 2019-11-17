package cz.finance.hr.test.core.rest.filter;

import com.google.common.net.InetAddresses;
import cz.finance.hr.test.config.IpLimitProperties;
import cz.finance.hr.test.core.rest.controller.response.IpLimitResponse;
import cz.finance.hr.test.core.service.IpLimitServiceImpl;
import cz.finance.hr.test.core.util.InetAddressToLongConverter;
import java.io.IOException;
import java.net.InetAddress;
import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Slf4j
@Component
public class IpLimitFilter implements Filter {

    private final IpLimitProperties ipLimitProperties;
    private final IpLimitServiceImpl limitService;

    @Autowired
    public IpLimitFilter(IpLimitServiceImpl limitService, IpLimitProperties ipLimitProperties) {
        this.limitService = limitService;
        this.ipLimitProperties = ipLimitProperties;
    }

    @PostConstruct
    public void init() {
        //Allow dependency injection from a filter
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

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

        log.info("IP of call: {}; {}; {}", remoteAddr, inetAddress, InetAddressToLongConverter.ipToLong(inetAddress));
        IpLimitResponse limits = limitService.resolveIpLimits(inetAddress, ipLimitProperties);
        log.info("Call from [{}, {}, {}] allowed={}", limits.getCityId(), limits.getRegionId(), limits.getCountryId(), limits.getAllowed());

        if (limits.getAllowed()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // If you really want to return just status without body:
            res.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());

            // Return usual spring error:
            //res.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too Many Requests");
        }
    }
}
