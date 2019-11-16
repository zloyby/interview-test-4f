package cz.finance.hr.test.core.rest.controller;

import cz.finance.hr.test.core.rest.controller.response.ErrorResponse;
import cz.finance.hr.test.exception.CommonException;
import cz.finance.hr.test.exception.NotFoundException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = {"cz.finance.hr.test.core.rest.controller"})
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        log.error("Error", ex);
        return jsonError("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException ex) {
        log.warn("Common Exception", ex);
        String reason = Optional.ofNullable(ex.getMessage()).orElse("Not supported");
        return jsonError(reason, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        log.warn("Not found. {}", ex.getMessage());
        String reason = Optional.ofNullable(ex.getMessage()).orElse("Not found");
        return jsonError(reason, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> jsonError(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ErrorResponse body = new ErrorResponse(status.value(), message, status.getReasonPhrase());
        return new ResponseEntity<>(body, headers, status);
    }
}
