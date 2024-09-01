package modak.challenge.notification_service.UnitTests.Implementation;

import modak.challenge.notification_service.Notification.Application.Implementation.RateLimiterImpl;
import modak.challenge.notification_service.Notification.Domain.RateLimitEntity;
import modak.challenge.notification_service.Notification.Infrastructure.Repository.IRateLimiterRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RateLimiterImplTest {
    @Mock
    private IRateLimiterRepo rateLimitRepository;

    @InjectMocks
    private RateLimiterImpl rateLimiter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isAllowed_NoRow_CreatesNewRecord() {
        // Arrange
        String type = "status";
        String username = "user123";
        RateLimitEntity newRateLimitEntity = RateLimitEntity.builder()
                .username(username)
                .type(type)
                .count(1)
                .updatedAt(Instant.now())
                .build();

        when(rateLimitRepository.findByUsernameAndType(username, type))
                .thenReturn(Optional.empty());
        when(rateLimitRepository.save(any(RateLimitEntity.class)))
                .thenReturn(newRateLimitEntity);

        // Act
        boolean allowed = rateLimiter.isAllowed(type, username);

        // Assert
        assertTrue(allowed);
    }

    @Test
    void isAllowed_WithinRateLimit_AllowsRequest() {
        // Arrange
        String type = "status";
        String username = "user123";
        RateLimitEntity existingRateLimitEntity = RateLimitEntity.builder()
                .username(username)
                .type(type)
                .count(1)
                .updatedAt(Instant.now().minusMillis(5000)) // 5 seconds ago
                .build();

        when(rateLimitRepository.findByUsernameAndType(username, type))
                .thenReturn(Optional.of(existingRateLimitEntity));
        when(rateLimitRepository.save(any(RateLimitEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Return the argument itself

        // Act
        boolean allowed = rateLimiter.isAllowed(type, username);

        // Assert
        assertTrue(allowed);
    }

    @Test
    void isAllowed_ExceedsRateLimit_DeniesRequest() {
        // Arrange
        String type = "status";
        String username = "user123";
        RateLimitEntity existingRateLimitEntity = RateLimitEntity.builder()
                .username(username)
                .type(type)
                .count(3) // Assume limit is 2
                .updatedAt(Instant.now().minusMillis(5000)) // 5 seconds ago
                .build();

        when(rateLimitRepository.findByUsernameAndType(username, type))
                .thenReturn(Optional.of(existingRateLimitEntity));

        // Act
        boolean allowed = rateLimiter.isAllowed(type, username);

        // Assert
        assertFalse(allowed);
    }

    @Test
    void isAllowed_OutsideWindow_UpdatesRecord() {
        // Arrange
        String type = "status";
        String username = "user123";
        RateLimitEntity existingRateLimitEntity = RateLimitEntity.builder()
                .username(username)
                .type(type)
                .count(2)
                .updatedAt(Instant.now().minusMillis(3600000)) // 1 hour ago
                .build();

        RateLimitEntity updatedRateLimitEntity = RateLimitEntity.builder()
                .username(username)
                .type(type)
                .count(1) // After the update
                .updatedAt(Instant.now())
                .build();

        when(rateLimitRepository.findByUsernameAndType(username, type))
                .thenReturn(Optional.of(existingRateLimitEntity));
        when(rateLimitRepository.save(any(RateLimitEntity.class)))
                .thenReturn(updatedRateLimitEntity);

        // Act
        boolean allowed = rateLimiter.isAllowed(type, username);

        // Assert
        assertTrue(allowed);
    }
}
