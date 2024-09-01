package modak.challenge.notification_service.Notification.Infrastructure.Ports;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modak.challenge.notification_service.Notification.Domain.NotificationEntity;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDetailInput {

        @NotBlank(message = "Type is required")
        private String type;

        @NotBlank(message = "Username is required")
        private String username;

        @NotBlank(message = "Message is required")
        private String message;

        public NotificationEntity toNotificationEntity() {
                return NotificationEntity.builder()
                        .type(this.type)
                        .username(this.username)
                        .message(this.message)
                        .instant(Instant.now())
                        .build();
        }
}