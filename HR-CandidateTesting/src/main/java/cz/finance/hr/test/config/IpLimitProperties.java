package cz.finance.hr.test.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "filter")
public class IpLimitProperties {
    @Value("${filter.limit.country:10000}")
    private Integer limitForCountry;
    @Value("${filter.limit.region:1000}")
    private Integer limitForRegion;
    @Value("${filter.limit.city:100}")
    private Integer limitForCity;
    @Value("${filter.limit.time.minutes:60}")
    private Integer limitTime;
}
