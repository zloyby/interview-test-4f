package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.controller.request.ConnectionRequest;
import by.zloy.db.browser.zeaver.controller.request.Pagination;
import by.zloy.db.browser.zeaver.controller.response.ConnectionResponse;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("v1.ConnectionController")
@RequestMapping(value = "/api/v1/connections", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "REST", description = "Operations")
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
        return ResponseEntity.ok(connectionService.getAllConnections(pagination.toPageRequest()));
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
        return ResponseEntity.ok(connectionService.createConnection(connectionRequest));
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
        final ConnectionResponse connection = connectionService.getConnection(id);
        return ResponseEntity.ok(connection);
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
        return ResponseEntity.ok(connectionService.updateConnection(id, connectionRequest));
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
