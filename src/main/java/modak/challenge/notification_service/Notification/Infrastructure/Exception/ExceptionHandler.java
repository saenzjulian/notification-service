package modak.challenge.notification_service.Notification.Infrastructure.Exception;

import modak.challenge.notification_service.Generic.Infrastructure.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerGenericException(Exception ex, WebRequest request) {
        var e = List.of(
                Issue.builder()
                        .title("exception")
                        .detail(ex.getMessage())
                        .build()
        );
        return ResponseHandler.fail(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerValidationException(MethodArgumentNotValidException ex) {
        List<Issue> fields = new ArrayList<>(
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> Issue.builder()
                                .title(error.getField())
                                .detail(error.getDefaultMessage())
                                .build()
                        )
                        .toList()
        );
        return ResponseHandler.fail(HttpStatus.BAD_REQUEST, fields);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Object> handleRateLimitExceededException(RateLimitExceededException ex) {
        var e = List.of(
                Issue.builder()
                        .title("exception")
                        .detail(ex.getMessage())
                        .build()
        );
        return ResponseHandler.fail(HttpStatus.TOO_MANY_REQUESTS, e);
    }
}
