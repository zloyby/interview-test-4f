package cz.finance.hr.test.core.service;

import com.google.common.collect.ImmutableList;
import cz.finance.hr.test.api.model.City;
import cz.finance.hr.test.api.model.Country;
import cz.finance.hr.test.api.model.Region;
import cz.finance.hr.test.api.service.LocationService;
import cz.finance.hr.test.core.model.CityEntity;
import cz.finance.hr.test.core.model.CountryEntity;
import cz.finance.hr.test.core.model.RegionEntity;
import cz.finance.hr.test.core.repository.CityRepository;
import cz.finance.hr.test.core.repository.CountryRepository;
import cz.finance.hr.test.core.repository.RegionRepository;
import cz.finance.hr.test.core.util.ModelMapperUtils;
import java.util.List;
import javax.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
@Transactional
@CacheConfig(cacheNames = {"location"})
public class LocationServiceImpl implements LocationService {

    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public LocationServiceImpl(CityRepository cityRepository, RegionRepository regionRepository, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        Iterable<CountryEntity> entities = countryRepository.findAll();
        return ModelMapperUtils.mapAll(ImmutableList.copyOf(entities), Country.class);
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<Region> getAllCountryRegions(@Nonnull Long countryId) {
        List<RegionEntity> entities = regionRepository.findAllByCountryId(countryId);
        return ModelMapperUtils.mapAll(ImmutableList.copyOf(entities), Region.class);
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<City> getAllRegionCities(@Nonnull Long regionId) {
        List<CityEntity> entities = cityRepository.findAllByRegionId(regionId);
        return ModelMapperUtils.mapAll(ImmutableList.copyOf(entities), City.class);
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<City> getAllCountryCities(@Nonnull Long countryId) {
        List<CityEntity> entities = cityRepository.findAllByCountryId(countryId);
        return ModelMapperUtils.mapAll(ImmutableList.copyOf(entities), City.class);
    }

}
