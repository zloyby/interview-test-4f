package by.zloy.db.browser.zeaver.controller.advice;

import by.zloy.db.browser.zeaver.controller.response.ErrorResponse;
import by.zloy.db.browser.zeaver.exception.NotFoundException;
import by.zloy.db.browser.zeaver.exception.ZeaverException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = {"by.zloy.db.browser.zeaver.controller"})
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        log.error("Error", ex);
        return jsonError("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PropertyReferenceException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleAll(PropertyReferenceException ex) {
        log.error("Bind Exception", ex);
        return jsonError("Validation error", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ZeaverException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(ZeaverException ex) {
        log.error("Zeaver Exception", ex);
        String reason = Optional.ofNullable(ex.getMessage()).orElse("Internal error");
        return jsonError(reason, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        log.error("NotFoundException Exception", ex);
        String reason = Optional.ofNullable(ex.getMessage()).orElse("Internal error");
        return jsonError(reason, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> jsonError(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse body = new ErrorResponse(status.value(), message, status.getReasonPhrase());
        return new ResponseEntity<>(body, headers, status);
    }
}
