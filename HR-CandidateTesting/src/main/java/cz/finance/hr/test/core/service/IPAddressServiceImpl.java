package cz.finance.hr.test.core.service;

import cz.finance.hr.test.api.model.City;
import cz.finance.hr.test.api.model.GpsCoordinates;
import cz.finance.hr.test.api.model.IpAddressRange;
import cz.finance.hr.test.api.service.IPAddressService;
import cz.finance.hr.test.core.model.CityEntity;
import cz.finance.hr.test.core.model.GpsCoordinatesEntity;
import cz.finance.hr.test.core.model.IpAddressRangeEntity;
import cz.finance.hr.test.core.repository.IpAddressRangeRepository;
import cz.finance.hr.test.core.util.InetAddressToLongConverter;
import cz.finance.hr.test.core.util.ModelMapperUtils;
import cz.finance.hr.test.exception.NotFoundException;
import java.net.InetAddress;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
@Transactional
@CacheConfig(cacheNames = {"ipaddress"})
public class IPAddressServiceImpl implements IPAddressService {

    private final IpAddressRangeRepository ipAddressRangeRepository;

    @Autowired
    public IPAddressServiceImpl(IpAddressRangeRepository ipAddressRangeRepository) {
        this.ipAddressRangeRepository = ipAddressRangeRepository;
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<IpAddressRange> getAllCityIPAddressRanges(@Nonnull Long cityId) {
        List<IpAddressRangeEntity> ranges = ipAddressRangeRepository.findAllByCityId(cityId);
        return ranges.stream().map(r -> new IpAddressRange(
                InetAddressToLongConverter.longToInetAddress(r.getFrom()),
                InetAddressToLongConverter.longToInetAddress(r.getTo()))).collect(Collectors.toList());
    }

    @Nullable
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public City guessCityForIPAddress(@Nonnull InetAddress ipAddress) {
        Long ipNumber = InetAddressToLongConverter.ipToLong(ipAddress);
        Optional<IpAddressRangeEntity> ipInsideRangeOptional = ipAddressRangeRepository.findIpInsideRange(ipNumber);
        IpAddressRangeEntity ipAddressRangeEntity = ipInsideRangeOptional.orElseThrow(NotFoundException::new);

        GpsCoordinatesEntity gpsCoordinatesEntity = ipAddressRangeEntity.getGpsCoordinatesEntity();
        CityEntity cityEntity = gpsCoordinatesEntity.getCity();
        City city = ModelMapperUtils.map(cityEntity, City.class);
        GpsCoordinates gpsCoordinates = ModelMapperUtils.map(gpsCoordinatesEntity, GpsCoordinates.class);
        city.setGpsCoordinates(gpsCoordinates);
        return city;
    }
}
