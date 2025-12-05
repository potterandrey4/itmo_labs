package itmo.soa.barsservice.exception;

import org.springframework.http.HttpStatus;

public class LabworkClientException extends RuntimeException {
    private final HttpStatus status;
    private final String responseBody;

    public LabworkClientException(HttpStatus status, String responseBody) {
        super(buildMessage(status, responseBody));
        this.status = status;
        this.responseBody = responseBody;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    private static String buildMessage(HttpStatus status, String responseBody) {
        String base = "External LabworkService request failed with status " + status;
        if (responseBody == null || responseBody.isBlank()) {
            return base;
        }
        return base + ": " + responseBody;
    }
}
