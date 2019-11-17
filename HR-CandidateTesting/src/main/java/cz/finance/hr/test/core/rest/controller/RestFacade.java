package cz.finance.hr.test.core.rest.controller;

import cz.finance.hr.test.api.model.City;
import cz.finance.hr.test.api.model.Country;
import cz.finance.hr.test.api.model.IpAddressRange;
import cz.finance.hr.test.api.model.Region;
import cz.finance.hr.test.api.service.IPAddressService;
import cz.finance.hr.test.api.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.InetAddress;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTfull facade over {@link LocationService} and {@link IPAddressService}.
 */
@RestController
@Api(tags = "REST", description = "Operations")
@Validated
//TODO: add pagination
public class RestFacade implements LocationService, IPAddressService {

    private final LocationService locationService;
    private final IPAddressService ipAddressService;

    @Autowired
    public RestFacade(LocationService locationService, IPAddressService ipAddressService) {
        this.locationService = locationService;
        this.ipAddressService = ipAddressService;
    }

    @RequestMapping(value = "country", method = RequestMethod.GET)
    @Override
    @Nonnull
    @ApiOperation(value = "Get all countries",
            nickname = "getAllCountries")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 429, message = "Too many requests"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<Country> getAllCountries() {
        return locationService.getAllCountries();
    }

    @RequestMapping(value = "country/{countryId}/region", method = RequestMethod.GET)
    @Override
    @Nonnull
    @ApiOperation(value = "Get all regions in country",
            nickname = "getAllCountryRegions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 429, message = "Too many requests"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<Region> getAllCountryRegions(
            @PathVariable("countryId") @ApiParam(example = "1", required = true) @Nonnull Long countryId) {
        return locationService.getAllCountryRegions(countryId);
    }

    @RequestMapping(value = "region/{regionId}/city", method = RequestMethod.GET)
    @Override
    @Nonnull
    @ApiOperation(value = "Get all cities in region",
            nickname = "getAllRegionCities")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 429, message = "Too many requests"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<City> getAllRegionCities(
            @PathVariable("regionId") @ApiParam(example = "2", required = true) @Nonnull Long regionId) {
        return locationService.getAllRegionCities(regionId);
    }

    @RequestMapping(value = "country/{countryId}/city", method = RequestMethod.GET)
    @Override
    @Nonnull
    @ApiOperation(value = "Get all cities in country",
            nickname = "getAllCountryCities")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 429, message = "Too many requests"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<City> getAllCountryCities(
            @PathVariable("countryId") @ApiParam(example = "1", required = true) @Nonnull Long countryId) {
        return locationService.getAllCountryCities(countryId);
    }

    @RequestMapping(value = "city/{cityId}/ipaddress", method = RequestMethod.GET)
    @Override
    @Nonnull
    @ApiOperation(value = "Get all IP ranges for city",
            nickname = "getAllCityIPAddressRanges")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 429, message = "Too many requests"),
            @ApiResponse(code = 500, message = "Failure")})
    public List<IpAddressRange> getAllCityIPAddressRanges(
            @PathVariable("cityId") @ApiParam(example = "3", required = true) @Nonnull Long cityId) {
        return ipAddressService.getAllCityIPAddressRanges(cityId);
    }

    @RequestMapping(value = "ipaddress/guess", method = RequestMethod.GET)
    @Override
    @Nullable
    @ApiOperation(value = "Get city by IP",
            nickname = "guessCityForIPAddress",
            response = City.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = City.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 429, message = "Too many requests"),
            @ApiResponse(code = 500, message = "Failure")})
    public City guessCityForIPAddress(
            @RequestParam("ip") @ApiParam(example = "2.16.44.1", required = true) @Nonnull InetAddress ipAddress) {
        return ipAddressService.guessCityForIPAddress(ipAddress);
    }
}
