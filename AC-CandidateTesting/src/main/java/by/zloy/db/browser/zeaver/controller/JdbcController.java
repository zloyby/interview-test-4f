package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.controller.response.ConnectionResponse;
import by.zloy.db.browser.zeaver.controller.response.SingleValueResponse;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import by.zloy.db.browser.zeaver.service.JdbcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("v1.JdbcController")
@RequestMapping(value = "/api/v1/jdbc", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "JDBC", description = "Operations")
@Slf4j
@Validated
public class JdbcController {

    final private ConnectionService connectionService;
    final private JdbcService jdbcService;

    @Autowired
    public JdbcController(ConnectionService connectionService, JdbcService jdbcService) {
        this.connectionService = connectionService;
        this.jdbcService = jdbcService;
    }

    @GetMapping(value = "/connections/{id}")
    @ApiOperation(value = "Test connection by ID",
            nickname = "testConnection")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<SingleValueResponse<Boolean>> testConnection(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") Long id
    ) {
        //Just check this id is valid:
        ConnectionResponse connection = connectionService.getConnection(id);
        String database = connection.getDatabase();
        log.info("Create connection url from {}", database);
        //TODO: create connection url

        Boolean isConnected = jdbcService.test(id);

        return ResponseEntity.ok(new SingleValueResponse<>(isConnected));
    }
}
