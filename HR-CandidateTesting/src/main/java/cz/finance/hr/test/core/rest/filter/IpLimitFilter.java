package cz.finance.hr.test.core.rest.filter;

import java.io.IOException;
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

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String remoteAddr = req.getRemoteAddr();
        log.info(remoteAddr);
        //TODO: convert IP for check?!
        //TODO: check IP has too many requests
        boolean b = true;
        if (b) {
            res.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too Many Requests");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
