package itmo.soa.barsservice.client;

import itmo.soa.barsservice.dto.LabWorkInput;
import itmo.soa.barsservice.exception.LabworkClientException;
import itmo.soa.barsservice.model.LabWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@Component
public class LabworkApiClient {
    private static final Logger log = LoggerFactory.getLogger(LabworkApiClient.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public LabworkApiClient(RestTemplate restTemplate,
            @Value("${labwork.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    public LabWork getLabWork(long id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment("labworks", String.valueOf(id))
                .build()
                .toUri();
        return exchange(uri, HttpMethod.GET, null);
    }

    public LabWork updateLabWork(long id, LabWorkInput payload) {
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .pathSegment("labworks", String.valueOf(id))
                .build()
                .toUri();
        return exchange(uri, HttpMethod.PUT, new HttpEntity<>(payload));
    }

    private LabWork exchange(URI uri, HttpMethod method, HttpEntity<?> entity) {
        try {
            ResponseEntity<LabWork> response = restTemplate.exchange(uri, method, entity, LabWork.class);
            return Objects.requireNonNull(response.getBody(), "Response body is empty");
        } catch (HttpStatusCodeException ex) {
            log.warn("Labwork API call failed: {} {} -> {}", method, uri, ex.getStatusCode());
            HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            throw new LabworkClientException(status, ex.getResponseBodyAsString());
        }
    }
}
