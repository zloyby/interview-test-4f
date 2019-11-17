package cz.finance.hr.test.core;

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
import cz.finance.hr.test.core.util.CsvParser;
import cz.finance.hr.test.core.util.GzipApache;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class DataLoaderRunner implements ApplicationRunner {

    private final ApplicationDataResourceProvider applicationDataResourceProvider;
    private final IpAddressRangeRepository ipAddressRangeRepository;
    private final GpsCoordinatesRepository gpsCoordinatesRepository;
    private final CityRepository cityRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public DataLoaderRunner(ApplicationDataResourceProvider applicationDataResourceProvider,
                            IpAddressRangeRepository ipAddressRangeRepository,
                            GpsCoordinatesRepository gpsCoordinatesRepository,
                            CityRepository cityRepository,
                            RegionRepository regionRepository,
                            CountryRepository countryRepository) {
        this.applicationDataResourceProvider = applicationDataResourceProvider;
        this.ipAddressRangeRepository = ipAddressRangeRepository;
        this.gpsCoordinatesRepository = gpsCoordinatesRepository;
        this.cityRepository = cityRepository;
        this.regionRepository = regionRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Start filling database from csv");
        List<Resource> dataFileResources = applicationDataResourceProvider.getDataFileResources();
        dataFileResources.forEach(this::readCsvFile);
    }

    /**
     * Load CSV from lines:
     * "34614272","34614527","CZ","Czech Republic","Praha, Hlavni mesto","Prague","50.088040","14.420760"
     * "85367296","85367551","SK","Slovakia","Nitriansky kraj","Zlate Moravce","48.385530","18.400630"
     * ...
     * Description:
     * | -------------  | ------------- | -----
     * | ip_from        | INT (10)      | First IP address in netblock.
     * | ip_to	        | INT (10)      | Last IP address in netblock.
     * | country_code   | CHAR(2)       | Two-character country code based on ISO 3166.
     * | country_name   | VARCHAR(64)   | Country name based on ISO 3166.
     * | region_name    | VARCHAR(128)  | Region or state name.
     * | city_name	    | VARCHAR(128)  | City name.
     * | GPS latitude   | DOUBLE	    | City latitude. Default to capital city latitude if city is unknown.
     * | GPS longitude  | DOUBLE	    | City longitude. Default to capital city longitude if city is unknown.
     */
    private void readCsvFile(final Resource resource) {
        try {
            File tmpFile = File.createTempFile("decompressed-", null);
            GzipApache.decompressGzip(resource.getFile(), tmpFile);
            Scanner scanner = new Scanner(tmpFile);
            while (scanner.hasNext()) {
                List<String> line = CsvParser.parseLine(scanner.nextLine());
                //TODO: fill database with bulk

                String ipFrom = line.get(0);
                String ipTo = line.get(1);
                String code = line.get(2);
                String country = line.get(3);
                String region = line.get(4);
                String city = line.get(5);
                Double lat = Double.valueOf(line.get(6));
                Double lon = Double.valueOf(line.get(7));

                Optional<CountryEntity> countryEntityOptional = countryRepository.findByCodeAndName(code, country);
                CountryEntity countryEntity = countryEntityOptional.orElse(CountryEntity.builder()
                        .name(country)
                        .code(code)
                        .build());
                countryRepository.save(countryEntity);

                Optional<RegionEntity> regionOptional = regionRepository.findByNameAndCountry(region, countryEntity);
                RegionEntity regionEntity = regionOptional.orElse(RegionEntity.builder()
                        .name(region)
                        .country(countryEntity)
                        .build());
                regionRepository.save(regionEntity);

                Optional<CityEntity> cityOptional = cityRepository.findByNameAndRegion(city, regionEntity);
                CityEntity cityEntity = cityOptional.orElse(CityEntity.builder()
                        .name(city)
                        .region(regionEntity)
                        .build());
                cityRepository.save(cityEntity);

                Optional<GpsCoordinatesEntity> gpsCoordinatesOptional = gpsCoordinatesRepository.findByLatitudeAndLongtitudeAndCity(lat, lon, cityEntity);
                GpsCoordinatesEntity gpsCoordinatesEntity = gpsCoordinatesOptional.orElse(GpsCoordinatesEntity
                        .builder()
                        .latitude(lat)
                        .longtitude(lon)
                        .city(cityEntity)
                        .build());
                gpsCoordinatesRepository.save(gpsCoordinatesEntity);

                IpAddressRangeEntity ipRangeEntity = IpAddressRangeEntity
                        .builder()
                        .from(Long.valueOf(ipFrom))
                        .to(Long.valueOf(ipTo))
                        .gpsCoordinatesEntity(gpsCoordinatesEntity)
                        .build();
                ipAddressRangeRepository.save(ipRangeEntity);

                log.info("[from=" + line.get(0) + ", to=" + line.get(1) + " , code=" + line.get(2) + "]");
            }
            scanner.close();
        } catch (IOException ex) {
            log.error("Can not parse CSV file " + resource.getFilename());
        }
    }
}
