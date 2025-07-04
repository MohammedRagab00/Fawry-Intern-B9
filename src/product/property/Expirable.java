package product.property;

import java.time.LocalDateTime;

public interface Expirable {
    LocalDateTime getExpiryDate();

    default boolean isExpired() {
        return LocalDateTime.now().isAfter(getExpiryDate());
    }
}
