package cz.finance.hr.test.core.service;

import cz.finance.hr.test.api.model.City;
import cz.finance.hr.test.api.model.Country;
import cz.finance.hr.test.api.model.Region;
import cz.finance.hr.test.api.service.LocationService;
import java.util.List;
import javax.annotation.Nonnull;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {

        //TODO implement me!
        return null;
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<Region> getAllCountryRegions(@Nonnull Long countryId) {
        //TODO implement me!
        return null;
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<City> getAllRegionCities(@Nonnull Long regionId) {
        //TODO implement me!
        return null;
    }

    @Nonnull
    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<City> getAllCountryCities(@Nonnull Long countryId) {
        //TODO implement me!
        return null;
    }

}
