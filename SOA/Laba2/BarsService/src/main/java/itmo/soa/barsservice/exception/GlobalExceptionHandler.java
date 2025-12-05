package itmo.soa.barsservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.soa.barsservice.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException ex, HttpServletRequest request) {
        String message = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(LabworkClientException.class)
    public ResponseEntity<ErrorResponse> handleLabworkClient(LabworkClientException ex, HttpServletRequest request) {
        ErrorResponse parsed = tryParseRemoteError(ex, request);
        return ResponseEntity.status(ex.getStatus()).body(parsed);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    private ErrorResponse tryParseRemoteError(LabworkClientException ex, HttpServletRequest request) {
        String body = ex.getResponseBody();
        if (body != null && !body.isBlank()) {
            try {
                ErrorResponse remote = objectMapper.readValue(body, ErrorResponse.class);
                if (remote.getTimestamp() == null) {
                    remote.setTimestamp(java.time.OffsetDateTime.now());
                }
                if (remote.getStatus() == 0) {
                    remote.setStatus(ex.getStatus().value());
                    remote.setError(ex.getStatus().getReasonPhrase());
                }
                if (remote.getPath() == null) {
                    remote.setPath(request.getRequestURI());
                }
                return remote;
            } catch (IOException ignored) {
            }
        }
        return ErrorResponse.from(ex.getStatus(), ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.from(status,
                message == null || message.isBlank() ? status.getReasonPhrase() : message,
                request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }
}
