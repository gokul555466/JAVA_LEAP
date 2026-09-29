package com.gokul.dronelocker.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage()));

        if (fieldErrors.isEmpty()) {
            return badRequest("Required fields must not be null or empty.");
        }

        if (fieldErrors.size() == 1) {
            Map.Entry<String, String> entry = fieldErrors.entrySet().iterator().next();
            return badRequest(entry.getValue());
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);
        body.put("message", "Validation failed");
        body.put("errors", fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String name = exception.getName();
        String message = name.equalsIgnoreCase("id") || name.toLowerCase().endsWith("id")
                ? "Invalid ID. Please enter a valid number."
                : "Invalid value for '" + name + "'. Please enter a valid " + getExpectedType(exception) + ".";
        return badRequest(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        String msg = exception.getMessage();
        if (msg != null && msg.contains("Cannot coerce Floating-point")) {
            return badRequest("Invalid request data. Decimal values are not allowed for integer fields.");
        }
        if (msg != null && msg.contains("Cannot deserialize value of type")) {
            return badRequest("Invalid request data. Please check the data types of your fields.");
        }
        return badRequest("Invalid request data.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParameter(
            MissingServletRequestParameterException exception) {
        return badRequest("Missing required request parameter: " + exception.getParameterName() + ".");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException exception) {
        String message = exception.getReason() == null
                ? "Request could not be processed."
                : exception.getReason();
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse(message));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException exception) {
        String msg = exception.getMostSpecificCause().getMessage();
        if (msg != null && msg.contains("Duplicate entry")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(errorResponse("A record with the same unique value already exists."));
        }
        return badRequest("Data integrity violation. Please check your input.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse("Resource not found."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException exception) {
        return badRequest(exception.getMessage() != null ? exception.getMessage() : "Invalid input.");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse("An unexpected error occurred. Please try again later."));
    }

    private ResponseEntity<Map<String, Object>> badRequest(String message) {
        return ResponseEntity.badRequest().body(errorResponse(message));
    }

    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);
        body.put("message", message);
        return body;
    }

    private String getExpectedType(MethodArgumentTypeMismatchException exception) {
        Class<?> type = exception.getRequiredType();
        if (type == null) return "value";
        if (Number.class.isAssignableFrom(type) || type == long.class || type == int.class || type == double.class) {
            return "number";
        }
        return "value";
    }
}
