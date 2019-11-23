package by.zloy.db.browser.zeaver.controller;

import by.zloy.db.browser.zeaver.controller.request.Pagination;
import by.zloy.db.browser.zeaver.controller.response.ConnectionResponse;
import by.zloy.db.browser.zeaver.service.ConnectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
@Api(tags = "REST", description = "Operations")
@Validated
public class ConnectionController {

    final private ConnectionService connectionService;

    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @RequestMapping(value = "/connections", method = RequestMethod.GET)
    @ApiOperation(value = "Get all connections",
            nickname = "getAllCountries")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")})
    public Page<ConnectionResponse> getAllConnections(
            @ModelAttribute @Validated Pagination pagination
    ) {
        return connectionService.getAllConnections(pagination.toPageRequest());
    }
}
