package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.controller.response.SingleValueResponse;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import by.zloy.db.browser.zeaver.service.JdbcService;
import by.zloy.db.browser.zeaver.service.dbcp.DataSourceBeanFactory;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController("v1.JdbcController")
@RequestMapping(value = "/api/v1/jdbc", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "JDBC", description = "Database operations")
@Slf4j
@Validated
public class JdbcController extends AbstractJdbcController {

    public JdbcController(ConnectionService connectionService,
                          JdbcService jdbcService,
                          DataSourceBeanFactory dataSourceBeanFactory) {
        super(connectionService, jdbcService, dataSourceBeanFactory);
    }

    @GetMapping(value = "/connections/{id}")
    @ApiOperation(value = "Get database info",
            nickname = "getCommonDatabaseInfo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getCommonDatabaseInfo(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id
    ) {
        return getQueryResult(id, jdbcOperations.getCommonDatabaseInfo());
    }

    @GetMapping(value = "/connections/{id}/databases")
    @ApiOperation(value = "Get all databases",
            nickname = "getDatabases")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getDatabases(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id
    ) {
        return getQueryResult(id, jdbcOperations.getDatabases());
    }

    @GetMapping(value = "/connections/{id}/schemas")
    @ApiOperation(value = "Get all schemas",
            nickname = "getSchemas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getSchemas(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id
    ) {
        return getQueryResult(id, jdbcOperations.getSchemas());
    }

    @GetMapping(value = "/connections/{id}/schemas/{schema}/tables")
    @ApiOperation(value = "Get tables of a schema",
            nickname = "getTables")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getTables(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id,
            @ApiParam(value = "Schema name", required = true, example = "billing") @PathVariable("schema") @NotEmpty String schema
    ) {
        return getQueryResult(id, jdbcOperations.getTables(schema));
    }

    @GetMapping(value = "/connections/{id}/schemas/{schema}/tables/statistic")
    @ApiOperation(value = "Get statistics about each table",
            nickname = "getTables")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getTablesStatistic(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id,
            @ApiParam(value = "Schema name", required = true, example = "billing") @PathVariable("schema") @NotEmpty String schema
    ) {
        return getQueryResult(id, jdbcOperations.getTableStatistics(schema));
    }

    @GetMapping(value = "/connections/{id}/schemas/{schema}/tables/{table}/columns")
    @ApiOperation(value = "Get columns of a table",
            nickname = "getColumns")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getColumns(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id,
            @ApiParam(value = "Schema name", required = true, example = "billing") @PathVariable("schema") @NotEmpty String schema,
            @ApiParam(value = "Schema name", required = true, example = "transactions") @PathVariable("table") @NotEmpty String table
    ) {
        return getQueryResult(id, jdbcOperations.getColumns(schema, table));
    }

    @GetMapping(value = "/connections/{id}/schemas/{schema}/tables/{table}/columns/statistic")
    @ApiOperation(value = "Get statistics about each column",
            nickname = "getColumnsStatistic")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getColumnsStatistic(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id,
            @ApiParam(value = "Schema name", required = true, example = "billing") @PathVariable("schema") @NotEmpty String schema,
            @ApiParam(value = "Schema name", required = true, example = "transactions") @PathVariable("table") @NotEmpty String table
    ) {
        return getQueryResult(id, jdbcOperations.getColumnStatistics(schema, table));
    }

    @GetMapping(value = "/connections/{id}/schemas/{schema}/tables/{table}/data")
    @ApiOperation(value = "Get data of table",
            nickname = "getData")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SingleValueResponse.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<List> getData(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") @NotNull Long id,
            @ApiParam(value = "Schema name", required = true, example = "billing") @PathVariable("schema") @NotEmpty String schema,
            @ApiParam(value = "Table name", required = true, example = "transactions") @PathVariable("table") @NotEmpty String table,
            @ApiParam(value = "Limit (max 100)", required = true, example = "50") @RequestParam(value = "limit", defaultValue = "50") @Max(100) @Min(0) Long limit,
            @ApiParam(value = "Offset", required = true, example = "0") @RequestParam(value = "offset", defaultValue = "0") @Min(0) Long offset
    ) {
        return getQueryResult(id, jdbcOperations.getData(schema, table, limit, offset));
    }
}
