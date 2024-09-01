package modak.challenge.notification_service.Notification.Infrastructure.Ports;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.Instant;

@Builder
public record NotificationDetailOutput(
        String type,
        @NotBlank
        String username,
        String message,
        Instant instant
) {
}
