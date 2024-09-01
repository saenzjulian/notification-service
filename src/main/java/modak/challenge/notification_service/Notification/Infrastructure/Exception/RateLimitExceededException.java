package modak.challenge.notification_service.Notification.Infrastructure.Exception;

public class RateLimitExceededException extends RuntimeException{
    public RateLimitExceededException(String message) {
        super(message);
    }
}
