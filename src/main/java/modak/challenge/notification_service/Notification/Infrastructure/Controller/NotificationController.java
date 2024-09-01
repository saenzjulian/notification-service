package modak.challenge.notification_service.Notification.Infrastructure.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import modak.challenge.notification_service.Generic.Infrastructure.ResponseHandler;
import modak.challenge.notification_service.Notification.Application.Service.INotification;
import modak.challenge.notification_service.Notification.Infrastructure.Ports.NotificationDetailInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notification")
@Tag(name = "Notification", description = "Operations related to notifications.")
public class NotificationController {

    private final INotification notificationService;

    public NotificationController(INotification notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<?> sendNotification(@Valid @RequestBody NotificationDetailInput input) {
        var output = notificationService.manage(input);
        return ResponseHandler.success(HttpStatus.OK, output);
    }
}
