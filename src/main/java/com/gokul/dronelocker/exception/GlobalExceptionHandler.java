package com.gokul.dronelocker.exception;

import java.util.LinkedHashMap;
import java.util.Locale;
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
                fieldErrors.putIfAbsent(error.getField(),
                        friendlyValidationMessage(error.getField(), error.getCode())));

        if (fieldErrors.isEmpty()) {
            return badRequest("This field is required.");
        }

        if (fieldErrors.size() == 1) {
            Map.Entry<String, String> entry = fieldErrors.entrySet().iterator().next();
            return badRequest(entry.getValue());
        }

        String summary = fieldErrors.entrySet().stream()
                .map(entry -> "• " + displayFieldName(entry.getKey()) + ": " + entry.getValue())
                .collect(java.util.stream.Collectors.joining("\n", "Please fix the following:\n", ""));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", false);
        body.put("message", summary);
        body.put("errors", fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        String name = exception.getName();
        String message = name.toLowerCase(Locale.ROOT).endsWith("id")
                ? "Please enter a valid ID."
                : "number".equals(getExpectedType(exception))
                        ? "Please enter a valid number."
                        : "Please select a valid option.";
        return badRequest(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        String msg = exception.getMessage();
        if (msg != null && msg.contains("Cannot coerce Floating-point")) {
            return badRequest("Please enter a valid number.");
        }
        return badRequest("Please check your information and try again.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParameter(
            MissingServletRequestParameterException exception) {
        return badRequest("This field is required.");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(ResponseStatusException exception) {
        String message = friendlyExceptionMessage(exception.getReason());
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse(message));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException exception) {
        String msg = exception.getMostSpecificCause().getMessage();
        if (msg != null && msg.contains("Duplicate entry")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(errorResponse("This value already exists."));
        }
        return badRequest("Unable to save this information. Please check your entries.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse("The requested record was not found."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException exception) {
        return badRequest(friendlyExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse("Something went wrong. Please try again."));
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

    private String friendlyValidationMessage(String field, String constraint) {
        String normalizedField = field.toLowerCase(Locale.ROOT);
        if ("NotBlank".equals(constraint) || "NotNull".equals(constraint)) {
            return "This field is required.";
        }
        if (normalizedField.contains("phone")) {
            return "Enter a valid 10-digit mobile number.";
        }
        if (normalizedField.contains("email")) {
            return "Enter a valid email address.";
        }
        if (normalizedField.contains("name")) {
            return "Please enter a valid name.";
        }
        if (normalizedField.contains("location") || normalizedField.contains("address")) {
            return "Please enter a valid location.";
        }
        if (normalizedField.endsWith("id")) {
            return "Please enter a valid ID.";
        }
        if (normalizedField.contains("status") || normalizedField.contains("type")
                || normalizedField.contains("method")) {
            return "Please select a valid option.";
        }
        if ("Positive".equals(constraint) || "Min".equals(constraint)) {
            return "Please enter a positive number.";
        }
        if (normalizedField.contains("date") || normalizedField.contains("time")) {
            return "Please enter a valid date.";
        }
        if ("PositiveOrZero".equals(constraint) || "DecimalMin".equals(constraint)
                || "DecimalMax".equals(constraint) || "Digits".equals(constraint)) {
            return "Please enter a valid number.";
        }
        if (normalizedField.endsWith("code") || normalizedField.contains("number")
                || normalizedField.contains("reference")) {
            return "Please enter a valid ID.";
        }
        return "Please enter a valid value.";
    }

    private String displayFieldName(String field) {
        String normalizedField = field.toLowerCase(Locale.ROOT);
        if (normalizedField.contains("recipient") && normalizedField.contains("name")
                || normalizedField.contains("customer") && normalizedField.contains("name")
                || normalizedField.contains("contact") && normalizedField.contains("name")
                || normalizedField.equals("vendorname") || normalizedField.equals("companyname")) {
            return "Name";
        }
        if (normalizedField.contains("phone")) return "Phone";
        if (normalizedField.contains("email")) return "Email";
        if (normalizedField.contains("location") || normalizedField.contains("address")) return "Location";
        if (normalizedField.endsWith("id")) return "ID";
        StringBuilder label = new StringBuilder();
        for (char character : field.toCharArray()) {
            if (Character.isUpperCase(character) && !label.isEmpty()) label.append(' ');
            label.append(character);
        }
        label.setCharAt(0, Character.toUpperCase(label.charAt(0)));
        return label.toString();
    }

    private String friendlyExceptionMessage(String reason) {
        if (reason == null || reason.isBlank()) {
            return "Please check your information and try again.";
        }
        String normalized = reason.toLowerCase(Locale.ROOT);
        if (normalized.contains("not found")) return "The requested record was not found.";
        if (normalized.contains("duplicate") || normalized.contains("already exists")) {
            return "This value already exists.";
        }
        if (normalized.contains("required") || normalized.contains("must not be blank")
                || normalized.contains("cannot be null")) {
            return "This field is required.";
        }
        if (normalized.contains("date") && (normalized.contains("before")
                || normalized.contains("after") || normalized.contains("range"))) {
            return "End date must be after start date.";
        }
        if (normalized.contains("passcode")) return "The passcode is incorrect.";
        if (normalized.contains("available bays")) return "Available bays cannot exceed total bays.";
        if (normalized.contains("tariff")) return "Please enter a valid number.";
        if (normalized.contains("positive") || normalized.contains("negative")) {
            return "Please enter a positive number.";
        }
        if (normalized.contains("status") || normalized.contains("option")) {
            return "Please select a valid option.";
        }
        return "Please check your information and try again.";
    }
}
