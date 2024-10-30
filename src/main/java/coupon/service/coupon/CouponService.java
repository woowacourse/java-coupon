package coupon.service.coupon;

import coupon.config.cache.RedisKey;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.discount.DiscountPolicy;
import coupon.domain.coupon.discount.DiscountType;
import coupon.domain.coupon.repository.CouponRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final RedisTemplate redisTemplate;

    @Transactional
    public Coupon createCoupon(String name, int discountPrice, int minOrderPrice,
                               DiscountType discountType, int minDiscountRange, int maxDiscountRange,
                               Category category, LocalDate issueStartDate, LocalDate issueEndDate) {
        Coupon coupon = new Coupon(name, discountType, minDiscountRange, maxDiscountRange, discountPrice, minOrderPrice, category, issueStartDate,
                issueEndDate);

        Coupon savedCoupon = couponRepository.save(coupon);
        String couponKey = RedisKey.COUPON.getKey(savedCoupon.getId());
        redisTemplate.opsForValue().set(couponKey, savedCoupon);

        return savedCoupon;
    }

    /**
     * 해당 메서드는 복제지연(Replication Lag) 해소가 필요한 메서드입니다.
     *
     * 복제지연 해소를 위해 Transactional을 readOnly=false로 설정합니다.
     * readOnly=false 는 WriterDB의 DataSource를 사용하며
     * readOnly=true 는 ReaderDB의 DataSource를 사용합니다.
     *
     * 자세한 설정은 DataSourceConfig.java 참조
     */
    @Transactional
    public Coupon getById(long couponId) {
        return couponRepository.getById(couponId);
    }
}
