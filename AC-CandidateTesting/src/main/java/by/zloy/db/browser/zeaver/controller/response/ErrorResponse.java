package by.zloy.db.browser.zeaver.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private long timestamp;
    private int status;
    private String message;
    private List<String> errors;

    public ErrorResponse(int status, String message, String error) {
        this(Instant.now().toEpochMilli(), status, message, Collections.singletonList(error));
    }
}
