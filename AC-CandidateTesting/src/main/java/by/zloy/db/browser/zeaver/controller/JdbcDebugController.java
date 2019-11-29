package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.controller.response.SingleValueResponse;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import by.zloy.db.browser.zeaver.service.JdbcService;
import by.zloy.db.browser.zeaver.service.dbcp.DataSourceBeanFactory;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController("v1.JdbcDebugController")
@RequestMapping(value = "/api/v1/debug/jdbc", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "DEBUG", description = "Debug operations")
@Slf4j
@Validated
@Profile("debug")
public class JdbcDebugController extends AbstractJdbcController {

    public JdbcDebugController(ConnectionService connectionService,
                               JdbcService jdbcService,
                               DataSourceBeanFactory dataSourceBeanFactory) {
        super(connectionService, jdbcService, dataSourceBeanFactory);
    }

    @PostMapping(value = "/connections/{id}/query")
    @ApiOperation(value = "Execute custom sql query",
            nickname = "executeQuery")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> executeQuery(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id,
            @ApiParam(value = "SQL query", required = true, example = "select 1") @RequestParam("query") @NotEmpty String query
    ) {
        return getQueryResult(id, query);
    }
}
