package modak.challenge.notification_service.Notification.Infrastructure.Exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Issue {
    private String title;
    private String detail;
}

