package modak.challenge.notification_service.Generic.Infrastructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseHandler<D, E> {
    private UUID id;
    private Instant timestamp;
    private int status;
    private String message;
    private Boolean error;
    private D data;
    private E errors;

    /**
     * Create a success response with data.
     */

    @SuppressWarnings("unchecked")
    public static <D, E> ResponseEntity<D> success(HttpStatus status, D data, String... customMessage) {
        var response = ResponseHandler.<D,E>builder()
                .id(UUID.randomUUID())
                .timestamp(Instant.now())
                .status(status.value())
                .message((customMessage != null && customMessage.length > 0) ? Arrays.toString(customMessage) : null)
                .error(null)
                .data(data)
                .build();
        return (ResponseEntity<D>)  new ResponseEntity<>(response, status);
    }
    /**
     * Create a failure response with errors.
     */
    @SuppressWarnings("unchecked")
    public static <D,E> ResponseEntity<E> fail(HttpStatus status, E errors) {
        var response = ResponseHandler.<D,E>builder()
                .id(UUID.randomUUID())
                .timestamp(Instant.now())
                .status(status.value())
                .message(status.getReasonPhrase())
                .error(true)
                .errors(errors)
                .build();
        return (ResponseEntity<E>) new ResponseEntity<>(response, status);
    }
}
