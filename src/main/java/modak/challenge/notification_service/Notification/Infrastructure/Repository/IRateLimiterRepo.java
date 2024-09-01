package modak.challenge.notification_service.Notification.Infrastructure.Repository;

import modak.challenge.notification_service.Generic.Infrastructure.Repository.IPostgreSQLGenericRepository;
import modak.challenge.notification_service.Notification.Domain.RateLimitEntity;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;


public interface IRateLimiterRepo extends IPostgreSQLGenericRepository<RateLimitEntity, Long> {
    Optional<RateLimitEntity> findByUsernameAndType(String userId, String type);

    @Query(value = """
            select
              case when count(r) > 0 then true else false end
            from rate_limit r
            where
              r.username = :username
              and r.type = :type
              and r.count < :maxCount
              and r.updated_at > :timestamp
            """, nativeQuery = true)
    boolean isAllowed(String username, String type, int maxCount, Instant timestamp);

}
