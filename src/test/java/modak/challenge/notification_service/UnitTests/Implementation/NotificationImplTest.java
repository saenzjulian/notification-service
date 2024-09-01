package modak.challenge.notification_service.UnitTests.Implementation;

import modak.challenge.notification_service.Notification.Application.Implementation.NotificationImpl;
import modak.challenge.notification_service.Notification.Application.Service.IRateLimiter;
import modak.challenge.notification_service.Notification.Domain.Enum.RateLimitRules;
import modak.challenge.notification_service.Notification.Domain.NotificationEntity;
import modak.challenge.notification_service.Notification.Infrastructure.Exception.RateLimitExceededException;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailInput;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailOutput;
import modak.challenge.notification_service.Notification.Infrastructure.Repository.INotificationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class NotificationImplTest {
    @Mock
    private INotificationRepo notificationRepository;

    @Mock
    private IRateLimiter rateLimiter;

    @InjectMocks
    private NotificationImpl notificationImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void manage_Success() {
        // Arrange
        NotificationDetailInput input = NotificationDetailInput.builder()
                .type(RateLimitRules.STATUS)
                .username("user123")
                .message("Status message")
                .build();

        NotificationEntity notificationEntity = input.toNotificationEntity();
        NotificationDetailOutput notificationDetailOutput = notificationEntity.toNotificationDetailOutput();

        when(rateLimiter.isAllowed(input.getType().getType(), input.getUsername())).thenReturn(true);
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(notificationEntity);

        // Act
        Optional<NotificationDetailOutput> result = notificationImpl.manage(input);

        // Assert
        assertEquals(Optional.of(notificationDetailOutput), result);
    }

    @Test
    void manage_RateLimitExceeded() {
        // Arrange
        NotificationDetailInput input = NotificationDetailInput.builder()
                .type(RateLimitRules.STATUS)
                .username("user123")
                .message("Status message")
                .build();

        when(rateLimiter.isAllowed(input.getType().getType(), input.getUsername())).thenReturn(false);

        // Act and Assert
        assertThrows(RateLimitExceededException.class, () -> notificationImpl.manage(input));
    }
}
