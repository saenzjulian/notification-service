package modak.challenge.notification_service.Notification.Application.Implementation;

import modak.challenge.notification_service.Generic.Implementation.PostgreSQLImpl;
import modak.challenge.notification_service.Generic.Infrastructure.Repository.IPostgreSQLGenericRepository;
import modak.challenge.notification_service.Notification.Application.Service.IRateLimiter;
import modak.challenge.notification_service.Notification.Domain.Enum.RateLimitRules;
import modak.challenge.notification_service.Notification.Domain.RateLimitEntity;
import modak.challenge.notification_service.Notification.Infrastructure.Repository.IRateLimiterRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class RateLimiterImpl extends PostgreSQLImpl<RateLimitEntity, Long> implements IRateLimiter {

    private final IRateLimiterRepo rateLimitRepository;

    public RateLimiterImpl(IRateLimiterRepo rateLimitRepository) {
        this.rateLimitRepository = rateLimitRepository;
    }

    @Override
    protected IPostgreSQLGenericRepository<RateLimitEntity, Long> getRepository() {
        return rateLimitRepository;
    }

    @Override
    @Transactional
    public boolean isAllowed(String type, String username) {
        Instant currentInstant = Instant.now();

        RateLimitRules rule = RateLimitRules.findByType(type);
        int rateLimit = rule.getLimit();
        long timeWindowMillis = rule.getTimeWindow() ;
        Instant timeWindowStart = currentInstant.minusMillis(timeWindowMillis);

        Optional<RateLimitEntity> rateLimitOptional = rateLimitRepository.findByUsernameAndType(username, type);

        if (rateLimitOptional.isPresent()) {
            RateLimitEntity rateLimitEntity = rateLimitOptional.get();
            if (isOutsideWindow(rateLimitEntity.getUpdatedAt(), timeWindowStart)) {
                return updateAndAllowRequest(rateLimitEntity, currentInstant, rateLimit);
            } else if (rateLimitEntity.getCount() < rateLimit) {
                return updateAndAllowRequest(rateLimitEntity, currentInstant, rateLimit);
            } else {
                // Exceeded rate limit
                return false;
            }
        } else {
            // No existing record, create a new one
            rateLimitRepository.save(
                    RateLimitEntity.builder()
                            .username(username)
                            .type(type)
                            .count(1)
                            .updatedAt(currentInstant)
                            .build()
            );
            return true;
        }
    }
    private boolean isOutsideWindow(Instant updatedAt, Instant windowStart) {
        return updatedAt.isBefore(windowStart);
    }

    private boolean updateAndAllowRequest(RateLimitEntity entity, Instant now, int limit) {
        entity.setCount(entity.getCount() + 1);
        entity.setUpdatedAt(now);
        rateLimitRepository.save(entity);
        return true;
    }
}
