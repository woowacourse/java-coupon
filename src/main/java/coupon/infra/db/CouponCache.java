package coupon.infra.db;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "coupon", timeToLive = 3L)
@RequiredArgsConstructor
public class CouponCache {
    @Id
    private final Long id;

    private final String name;

    private final int discountMoney;

    private final int minOrderMoney;

    private final String category;

    private final LocalDate startDate;

    private final LocalDate endDate;
}
