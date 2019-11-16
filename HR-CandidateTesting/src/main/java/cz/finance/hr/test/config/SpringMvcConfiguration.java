package cz.finance.hr.test.config;

import cz.finance.hr.test.core.rest.converter.InetAddressConverter;
import cz.finance.hr.test.core.rest.filter.IpLimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {

    private final IpLimitFilter ipLimitFilter;

    @Autowired
    public SpringMvcConfiguration(IpLimitFilter ipLimitFilter) {
        this.ipLimitFilter = ipLimitFilter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverter(new InetAddressConverter());
    }

    @Bean
    public FilterRegistrationBean<IpLimitFilter> loggingFilter() {
        FilterRegistrationBean<IpLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(ipLimitFilter);
        registrationBean.addUrlPatterns("/city/*", "/country", "/country/*", "/ipaddress/*", "/region/*");
        return registrationBean;
    }
}
