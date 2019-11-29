package by.zloy.db.browser.zeaver.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Value
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private long timestamp;
    private int status;
    private String message;
    private List<String> errors;
    private List<FieldError> fieldErrors;

    public ErrorResponse(int status, String message, String error, List<FieldError> fieldErrors) {
        this(Instant.now().toEpochMilli(), status, message, Collections.singletonList(error), fieldErrors);
    }
}
