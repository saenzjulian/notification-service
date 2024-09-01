package modak.challenge.notification_service.Notification.Infrastructure.Repository;

import modak.challenge.notification_service.Generic.Infrastructure.Repository.IPostgreSQLGenericRepository;
import modak.challenge.notification_service.Notification.Domain.NotificationEntity;

public interface INotificationRepo extends IPostgreSQLGenericRepository<NotificationEntity, Long> {
}
