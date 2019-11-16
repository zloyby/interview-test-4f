package cz.finance.hr.test.core.service;

import cz.finance.hr.test.config.IpLimitProperties;
import cz.finance.hr.test.core.model.CityEntity;
import cz.finance.hr.test.core.model.GpsCoordinatesEntity;
import cz.finance.hr.test.core.model.IpAddressRangeEntity;
import cz.finance.hr.test.core.model.RequestsTransactionEntity;
import cz.finance.hr.test.core.model.response.IpLimitResponse;
import cz.finance.hr.test.core.repository.IpAddressRangeRepository;
import cz.finance.hr.test.core.repository.RequestsTransactionRepository;
import cz.finance.hr.test.core.util.InetAddressToLongConverter;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IpLimitServiceImpl {

    private final IpAddressRangeRepository ipAddressRangeRepository;
    private final RequestsTransactionRepository requestsTransactionRepository;

    @Autowired
    public IpLimitServiceImpl(IpAddressRangeRepository ipAddressRangeRepository, RequestsTransactionRepository requestsTransactionRepository) {
        this.ipAddressRangeRepository = ipAddressRangeRepository;
        this.requestsTransactionRepository = requestsTransactionRepository;
    }

    public cz.finance.hr.test.core.rest.controller.response.IpLimitResponse resolveIpLimits(InetAddress ipAddress, IpLimitProperties ipLimitProperties) {
        Long ipNumber = InetAddressToLongConverter.ipToLong(ipAddress);
        Optional<IpAddressRangeEntity> ipInsideRangeOptional = ipAddressRangeRepository.findIpInsideRangeWithFetch(ipNumber);

        if (!ipInsideRangeOptional.isPresent()) {
            // Allow for unknown location
            return cz.finance.hr.test.core.rest.controller.response.IpLimitResponse.builder().ip(ipNumber).allowed(true).build();
        } else {
            IpAddressRangeEntity ipAddressRangeEntity = ipInsideRangeOptional.get();
            GpsCoordinatesEntity gpsCoordinatesEntity = ipAddressRangeEntity.getGpsCoordinatesEntity();
            CityEntity cityEntity = gpsCoordinatesEntity.getCity();

            Long cityId = cityEntity.getId();
            Long regionId = cityEntity.getRegion().getId();
            Long countryId = cityEntity.getRegion().getCountry().getId();

            RequestsTransactionEntity entity = RequestsTransactionEntity.builder().ip(ipNumber).cityId(cityId).regionId(regionId).countryId(countryId).build();
            requestsTransactionRepository.save(entity);

            Calendar timeNowMinusLimit = Calendar.getInstance();
            timeNowMinusLimit.add(Calendar.MINUTE, -ipLimitProperties.getLimitTime());
            IpLimitResponse summaryResponse =
                    requestsTransactionRepository.findLimitsByLast(countryId, regionId, cityId, timeNowMinusLimit.getTimeInMillis());

            boolean isDeclined = summaryResponse.getCountCountries() > ipLimitProperties.getLimitForCountry()
                    || summaryResponse.getCountRegions() > ipLimitProperties.getLimitForRegion()
                    || summaryResponse.getCountCities() > ipLimitProperties.getLimitForCity();

            return cz.finance.hr.test.core.rest.controller.response.IpLimitResponse.builder()
                    .ip(ipNumber)
                    .cityId(cityId)
                    .regionId(regionId)
                    .countryId(countryId)
                    .allowed(!isDeclined)
                    .build();
        }
    }
}
