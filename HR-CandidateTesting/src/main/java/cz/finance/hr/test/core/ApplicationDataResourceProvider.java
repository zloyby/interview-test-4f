package cz.finance.hr.test.core;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Provider for CSV data with locations and their IP address ranges.
 *
 * <p>
 * Source data has been downloaded from http://lite.ip2location.com/.
 * </p>
 */
@Component
public class ApplicationDataResourceProvider {

    private final ResourceLoader resourceLoader;

    @Autowired
    public ApplicationDataResourceProvider(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<Resource> getDataFileResources() {
        return Arrays.asList(
                resourceLoader.getResource("classpath:data/ip2location-lite-db5-cz.csv.gz"),
                resourceLoader.getResource("classpath:data/ip2location-lite-db5-sk.csv.gz"));
    }

/*
"34614272","34614527","CZ","Czech Republic","Praha, Hlavni mesto","Prague","50.088040","14.420760"
...
"85367296","85367551","SK","Slovakia","Nitriansky kraj","Zlate Moravce","48.385530","18.400630"
*/

}
