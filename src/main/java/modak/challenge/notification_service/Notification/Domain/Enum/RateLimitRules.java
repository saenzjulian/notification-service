package modak.challenge.notification_service.Notification.Domain.Enum;

import lombok.Getter;

@Getter
public enum RateLimitRules {
    NEWS("news", 1, 86_400_000),
    MARKETING("marketing", 3, 3_600_000),
    STATUS("status", 2, 60_000);

    private final String type;
    private final int limit;
    private final long timeWindow;

    RateLimitRules(String type, int limit, long timeWindow) {
        this.type = type;
        this.limit = limit;
        this.timeWindow = timeWindow;
    }

    public static RateLimitRules findByType(String type) {
        for (RateLimitRules rule : values()) {
            if (rule.getType().equalsIgnoreCase(type)) {
                return rule;
            }
        }
        throw new IllegalArgumentException("No rate limit rule found for type: " + type);
    }
}
