package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.controller.request.ConnectionRequest;
import by.zloy.db.browser.zeaver.controller.request.Pagination;
import by.zloy.db.browser.zeaver.controller.response.ConnectionResponse;
import by.zloy.db.browser.zeaver.model.Connection;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import by.zloy.db.browser.zeaver.util.ModelMapperUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("v1.ConnectionController")
@RequestMapping(value = "/api/v1/connections", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "REST", description = "Connection operations")
@Slf4j
@Validated
public class ConnectionController {

    final private ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @GetMapping
    @ApiOperation(value = "Get all connections",
            nickname = "getAllCountries")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<Page<ConnectionResponse>> getAllConnections(
            @ModelAttribute @Validated Pagination pagination
    ) {
        final Page<Connection> connections = connectionService.getAllConnections(pagination.toPageRequest());
        log.info("found {}", connections.getTotalElements());
        return ResponseEntity.ok(ModelMapperUtils.mapAllPages(connections, ConnectionResponse.class));
    }

    @PostMapping()
    @ApiOperation(value = "Create connection",
            nickname = "createConnection")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<ConnectionResponse> createConnection(
            @ApiParam(required = true) @RequestBody @Valid ConnectionRequest connectionRequest
    ) {
        final Connection saved = connectionService.createConnection(connectionRequest);
        log.info("save Connection {}", saved.getName());
        return ResponseEntity.ok(ModelMapperUtils.map(saved, ConnectionResponse.class));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Get connection",
            nickname = "getConnection")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<ConnectionResponse> getConnection(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") Long id
    ) {
        final Connection connection = connectionService.getConnection(id);
        log.info("found Connection {}", connection.getName());
        return ResponseEntity.ok(ModelMapperUtils.map(connection, ConnectionResponse.class));
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Update connection",
            nickname = "updateConnection")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<ConnectionResponse> updateConnection(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") Long id,
            @ApiParam(required = true) @RequestBody @Valid ConnectionRequest connectionRequest
    ) {
        final Connection saved = connectionService.updateConnection(id, connectionRequest);
        log.info("update Connection {}", saved.getName());
        return ResponseEntity.ok(ModelMapperUtils.map(saved, ConnectionResponse.class));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete connection",
            nickname = "deleteConnection")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    public ResponseEntity<?> deleteConnection(
            @ApiParam(value = "Id of connection", required = true, example = "1") @PathVariable("id") Long id
    ) {
        connectionService.deleteConnection(id);
        return ResponseEntity.noContent().build();
    }
}
