package modak.challenge.notification_service.Notification.Infrastructure.Ports;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import modak.challenge.notification_service.Notification.Domain.Enum.RateLimitRules;

import java.time.Instant;

@Builder
public record NotificationDetailOutput(
        RateLimitRules type,
        @NotBlank
        String username,
        String message,
        Instant instant
) {
}
