package modak.challenge.notification_service.Notification.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import modak.challenge.notification_service.Notification.Domain.Enum.RateLimitRules;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailOutput;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RateLimitRules type;
    private String username;
    private String message;
    private Instant instant;

    public NotificationDetailOutput toNotificationDetailOutput(){
        return NotificationDetailOutput.builder()
                .type(type)
                .username(username)
                .message(message)
                .instant(instant)
                .build();
    }
}
