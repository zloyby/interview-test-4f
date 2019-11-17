package cz.finance.hr.test.core.rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class RestFacadeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loadAllRecordsAndMakeCallsTest() throws Exception {
        //TODO: refactor and load smaller test database or csv files
        this.mockMvc.perform(get("/country"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Czech Republic")))
                .andExpect(jsonPath("$[1].name", is("Slovakia")));

        this.mockMvc.perform(get("/country/1/region"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(15)))
                .andExpect(jsonPath("$[0].name", is("Praha, Hlavni mesto")))
                .andExpect(jsonPath("$[0].countryId", is(1)));

        this.mockMvc.perform(get("/region/1/city"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(11)))
                .andExpect(jsonPath("$[0].name", is("Prague")))
                .andExpect(jsonPath("$[0].regionId", is(1)));

        this.mockMvc.perform(get("/country/1/city"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(782)))
                .andExpect(jsonPath("$[0].name", is("Prague")))
                .andExpect(jsonPath("$[0].regionId", is(1)));


        this.mockMvc.perform(get("/city/1/ipaddress"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2785)))
                .andExpect(jsonPath("$[0].from", is("2.16.44.0")))
                .andExpect(jsonPath("$[0].to", is("2.16.44.255")));

        this.mockMvc.perform(get("/ipaddress/guess?ip=2.16.44.1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Prague")));
    }
}