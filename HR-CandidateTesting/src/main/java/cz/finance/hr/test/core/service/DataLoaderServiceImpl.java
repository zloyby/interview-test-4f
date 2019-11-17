package cz.finance.hr.test.core.service;

import cz.finance.hr.test.core.model.CityEntity;
import cz.finance.hr.test.core.model.CountryEntity;
import cz.finance.hr.test.core.model.GpsCoordinatesEntity;
import cz.finance.hr.test.core.model.IpAddressRangeEntity;
import cz.finance.hr.test.core.model.RegionEntity;
import cz.finance.hr.test.core.repository.CityRepository;
import cz.finance.hr.test.core.repository.CountryRepository;
import cz.finance.hr.test.core.repository.GpsCoordinatesRepository;
import cz.finance.hr.test.core.repository.IpAddressRangeRepository;
import cz.finance.hr.test.core.repository.RegionRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataLoaderServiceImpl {

    private final IpAddressRangeRepository ipAddressRangeRepository;
    private final GpsCoordinatesRepository gpsCoordinatesRepository;
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public DataLoaderServiceImpl(
            IpAddressRangeRepository ipAddressRangeRepository,
            GpsCoordinatesRepository gpsCoordinatesRepository,
            CityRepository cityRepository,
            RegionRepository regionRepository,
            CountryRepository countryRepository) {
        this.ipAddressRangeRepository = ipAddressRangeRepository;
        this.gpsCoordinatesRepository = gpsCoordinatesRepository;
        this.cityRepository = cityRepository;
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
    }

    @Cacheable("CountryEntity")
    public CountryEntity findByCodeAndName(String code, String country) {
        Optional<CountryEntity> optional = countryRepository.findByCodeAndName(code, country);
        return optional.orElseGet(() -> countryRepository.save(
                CountryEntity.builder()
                        .name(country)
                        .code(code)
                        .build()));
    }

    @Cacheable("RegionEntity")
    public RegionEntity findByNameAndCountry(String region, CountryEntity countryEntity) {
        Optional<RegionEntity> optional = regionRepository.findByNameAndCountry(region, countryEntity);
        return optional.orElseGet(() -> regionRepository.save(
                RegionEntity.builder()
                        .name(region)
                        .country(countryEntity)
                        .build()));
    }

    @Cacheable("CityEntity")
    public CityEntity findByNameAndRegion(String city, RegionEntity regionEntity) {
        Optional<CityEntity> optional = cityRepository.findByNameAndRegion(city, regionEntity);
        return optional.orElseGet(() -> cityRepository.save(CityEntity.builder()
                .name(city)
                .region(regionEntity)
                .build()));
    }

    @Cacheable("GpsCoordinatesEntity")
    public GpsCoordinatesEntity findByLatitudeAndLongtitudeAndCity(Double lat, Double lon, CityEntity cityEntity) {
        Optional<GpsCoordinatesEntity> optional = gpsCoordinatesRepository.findByLatitudeAndLongtitudeAndCity(lat, lon, cityEntity);
        return optional.orElseGet(() -> gpsCoordinatesRepository.save(GpsCoordinatesEntity
                .builder()
                .latitude(lat)
                .longtitude(lon)
                .city(cityEntity)
                .build()));
    }


    public void createIpRange(String ipFrom, String ipTo, GpsCoordinatesEntity gpsCoordinatesEntity) {
        IpAddressRangeEntity entity = IpAddressRangeEntity
                .builder()
                .from(Long.valueOf(ipFrom))
                .to(Long.valueOf(ipTo))
                .gpsCoordinatesEntity(gpsCoordinatesEntity)
                .build();
        ipAddressRangeRepository.save(entity);
    }
}
