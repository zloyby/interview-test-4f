package cz.finance.hr.test.core.rest.controller;

import cz.finance.hr.test.api.model.City;
import cz.finance.hr.test.api.model.Country;
import cz.finance.hr.test.api.model.IpAddressRange;
import cz.finance.hr.test.api.model.Region;
import cz.finance.hr.test.api.service.IPAddressService;
import cz.finance.hr.test.api.service.LocationService;
import java.net.InetAddress;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTfull facade over {@link LocationService} and {@link IPAddressService}.
 */
@RestController
public class RestFacade implements LocationService, IPAddressService {

    private final LocationService locationService;
    private final IPAddressService ipAddressService;

    @Autowired
    public RestFacade(LocationService locationService, IPAddressService ipAddressService) {
        this.locationService = locationService;
        this.ipAddressService = ipAddressService;
    }

    //region LocationService

    @RequestMapping(value = "country", method = RequestMethod.GET)
    @Override
    @Nonnull
    public List<Country> getAllCountries() {
        return locationService.getAllCountries();
    }

    @RequestMapping(value = "country/{countryId}/region", method = RequestMethod.GET)
    @Override
    @Nonnull
    public List<Region> getAllCountryRegions(@PathVariable("countryId") @Nonnull Long countryId) {
        return locationService.getAllCountryRegions(countryId);
    }

    @RequestMapping(value = "region/{regionId}/city", method = RequestMethod.GET)
    @Override
    @Nonnull
    public List<City> getAllRegionCities(@PathVariable("regionId") @Nonnull Long regionId) {
        return locationService.getAllRegionCities(regionId);
    }

    @RequestMapping(value = "country/{countryId}/city", method = RequestMethod.GET)
    @Override
    @Nonnull
    public List<City> getAllCountryCities(@PathVariable("countryId") @Nonnull Long countryId) {
        return locationService.getAllCountryCities(countryId);
    }

    //endregion


    //region IPAddressService

    @RequestMapping(value = "city/{cityId}/ipaddress", method = RequestMethod.GET)
    @Override
    @Nonnull
    public List<IpAddressRange> getAllCityIPAddressRanges(@PathVariable("cityId") @Nonnull Long cityId) {
        return ipAddressService.getAllCityIPAddressRanges(cityId);
    }

    @RequestMapping(value = "ipaddress/guess", method = RequestMethod.GET)
    @Override
    @Nullable
    public City guessCityForIPAddress(@RequestParam("ip") @Nonnull InetAddress ipAddress) {
        return ipAddressService.guessCityForIPAddress(ipAddress);
    }

    //endregion
}
