package modak.challenge.notification_service.Notification.Application.Implementation;

import modak.challenge.notification_service.Generic.Implementation.PostgreSQLImpl;
import modak.challenge.notification_service.Generic.Infrastructure.Repository.IPostgreSQLGenericRepository;
import modak.challenge.notification_service.Notification.Application.Service.IRateLimiter;
import modak.challenge.notification_service.Notification.Application.Service.INotification;
import modak.challenge.notification_service.Notification.Domain.NotificationEntity;
import modak.challenge.notification_service.Notification.Infrastructure.Exception.RateLimitExceededException;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailInput;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailOutput;
import modak.challenge.notification_service.Notification.Infrastructure.Repository.INotificationRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationImpl extends PostgreSQLImpl<NotificationEntity, Long> implements INotification {

    private final INotificationRepo notificationRepository;
    private final IRateLimiter rateLimiter;

    public NotificationImpl(INotificationRepo notificationRepository,
                            IRateLimiter rateLimiter){
        this.notificationRepository = notificationRepository;
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected IPostgreSQLGenericRepository<NotificationEntity, Long> getRepository() {
        return notificationRepository;
    }

    @Override
    public Optional<NotificationDetailOutput> manage(NotificationDetailInput input) {
        if(!rateLimiter.isAllowed(input.getType().getType(), input.getUsername())){
            throw new RateLimitExceededException("Rate limit exceeded");
        }

        var notification = notificationRepository.save(input.toNotificationEntity());
        return Optional.of(notification.toNotificationDetailOutput());
    }
}
