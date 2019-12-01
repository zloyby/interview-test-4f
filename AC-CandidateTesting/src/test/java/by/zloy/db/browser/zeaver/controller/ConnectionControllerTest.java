package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.controller.request.ConnectionRequest;
import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.model.Driver;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ConnectionController.class)
public class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConnectionService connectionService;

    @Test
    public void createConnection_returnEntity() throws Exception {
        final String testName = "Test DB Name";

        Connection createdStub = Connection.builder().id(1L).name(testName).build();
        when(connectionService.createConnection(any(ConnectionRequest.class))).thenReturn(createdStub);

        final ConnectionRequest request = ConnectionRequest.builder()
                .name(testName).driver(Driver.MYSQL.name()).host("127.0.0.1").port("3306")
                .database("zeaver").user("root").password("root").build();
        final MockHttpServletRequestBuilder postMock = post("/api/v1/connections")
                .content(objectMapper.writeValueAsBytes(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE);

        this.mockMvc.perform(postMock)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testName)));
    }
}