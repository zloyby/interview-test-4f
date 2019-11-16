package cz.finance.hr.test.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IpLimitResponse {
    private Long countCountries;
    private Long countRegions;
    private Long countCities;
}
