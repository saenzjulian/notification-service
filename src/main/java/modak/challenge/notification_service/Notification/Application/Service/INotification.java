package modak.challenge.notification_service.Notification.Application.Service;

import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailInput;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailOutput;

import java.util.Optional;

public interface INotification {
    Optional<NotificationDetailOutput> manage(NotificationDetailInput input);
}
