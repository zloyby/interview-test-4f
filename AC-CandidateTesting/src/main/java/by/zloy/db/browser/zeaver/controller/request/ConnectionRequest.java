package by.zloy.db.browser.zeaver.controller.request;

import by.zloy.db.browser.zeaver.model.Driver;
import by.zloy.db.browser.zeaver.util.EnumValid;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectionRequest {

    @ApiModelProperty(value = "Name", example = "My mysql", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "Driver", example = "MYSQL", required = true)
    @EnumValid(enumClass = Driver.class)
    private String driver;

    @ApiModelProperty(value = "Host", example = "127.0.0.1", required = true)
    @NotEmpty
    private String host;

    @ApiModelProperty(value = "Port", example = "3306", required = true)
    @NotEmpty
    private String port;

    @ApiModelProperty(value = "Database name", example = "zeaver", required = true)
    @NotEmpty
    private String database;

    @ApiModelProperty(value = "User name", example = "root", required = true)
    @NotEmpty
    private String user;

    @ApiModelProperty(value = "Password", example = "pwd", required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "Parameters", example = "?param1=value1&param2=value2")
    private String parameters;
}
