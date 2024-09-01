package modak.challenge.notification_service.UnitTests;

import modak.challenge.notification_service.Notification.Application.Service.INotification;
import modak.challenge.notification_service.Notification.Domain.Enum.RateLimitRules;
import modak.challenge.notification_service.Notification.Infrastructure.Controller.NotificationController;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailInput;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class NotificationControllerTest {
    @Mock
    private INotification notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotification_Success() {
        // Arrange
        NotificationDetailInput input = NotificationDetailInput.builder()
                .type(RateLimitRules.STATUS)
                .username("user123")
                .message("Status message")
                .build();

        NotificationDetailOutput expectedOutput = input.toNotificationEntity().toNotificationDetailOutput();
        // Directly mock the service to return the NotificationDetailOutput
        when(notificationService.manage(any(NotificationDetailInput.class)))
                .thenReturn(Optional.of(expectedOutput));

        // Act
        ResponseEntity<?> response = notificationController.sendNotification(input);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
