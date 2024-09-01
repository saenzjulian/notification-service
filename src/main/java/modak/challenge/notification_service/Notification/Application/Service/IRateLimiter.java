package modak.challenge.notification_service.Notification.Application.Service;

public interface IRateLimiter {
    boolean isAllowed(String type, String userId);
}
