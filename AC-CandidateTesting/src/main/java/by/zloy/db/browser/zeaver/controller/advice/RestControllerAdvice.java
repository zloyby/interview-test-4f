package by.zloy.db.browser.zeaver.controller.advice;

import by.zloy.db.browser.zeaver.controller.response.ErrorResponse;
import by.zloy.db.browser.zeaver.exception.DatabaseConnectionException;
import by.zloy.db.browser.zeaver.exception.NotFoundException;
import by.zloy.db.browser.zeaver.exception.ZeaverException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Invalid arguments. {}", ex.getMessage());
        return jsonError("Validation errors", HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldErrors());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Invalid field binding. {}", ex.getMessage());
        return jsonError("Validation errors", HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldErrors());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("Invalid request. {}", ex.getMessage());
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            List<JsonMappingException.Reference> path = ife.getPath();
            String reason = String.format("Invalid value '%s' for field '%s'", ife.getValue(), path.get(path.size() - 1).getFieldName());
            return jsonError(reason, HttpStatus.BAD_REQUEST, null);
        } else if (cause instanceof JsonMappingException || cause instanceof JsonParseException) {
            return jsonError("Invalid JSON", HttpStatus.BAD_REQUEST, null);
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        log.error("Error", ex);
        return jsonError("The was an error. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(ZeaverException.class)
    public ResponseEntity<Object> handleCommonException(ZeaverException ex) {
        log.error("Zeaver Exception", ex);
        String reason = Optional.ofNullable(ex.getMessage()).orElse("The was an error. Please try again later.");
        return jsonError(reason, HttpStatus.NOT_IMPLEMENTED, null);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException Exception", ex);
        String reason = Optional.ofNullable(ex.getMessage()).orElse("Unable to find the object");
        return jsonError(reason, HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler({PropertyReferenceException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleBindModelExceptions(Exception ex) {
        log.error("Bind Exception", ex);
        return jsonError("Validation error", HttpStatus.BAD_REQUEST, null);
    }

    @ExceptionHandler({SQLException.class, DatabaseConnectionException.class, BadSqlGrammarException.class})
    public ResponseEntity<Object> handleDatabaseConnectionException(SQLException ex) {
        log.error("Database Exception", ex);
        String reason = Optional.ofNullable(ex.getMessage()).orElse("Error with database");
        return jsonError(reason, HttpStatus.GATEWAY_TIMEOUT, null);
    }

    private ResponseEntity<Object> jsonError(String message, HttpStatus status, List<FieldError> fieldErrors) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse body = new ErrorResponse(status.value(), message, status.getReasonPhrase(), fieldErrors);
        return new ResponseEntity<>(body, headers, status);
    }
}
