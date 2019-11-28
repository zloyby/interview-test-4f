package by.zloy.db.browser.zeaver.controller.response;

import by.zloy.db.browser.zeaver.model.Driver;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectionResponse {
    private Long id;
    private String name;
    private Driver driver;
    private String host;
    private String port;
    private String database;
    private String user;
    private String parameters;
    private Long created;
    private Long updated;
}
