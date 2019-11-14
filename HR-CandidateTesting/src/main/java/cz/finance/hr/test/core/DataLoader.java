package cz.finance.hr.test.core;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ApplicationDataResourceProvider applicationDataResourceProvider;

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
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Start filling database from csv");
        List<Resource> dataFileResources = applicationDataResourceProvider.getDataFileResources();
        //TODO: parse csv and fill database
    }
}
