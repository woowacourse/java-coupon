package coupon.service;

import coupon.domain.usercoupon.UserCoupon;
import coupon.entity.UserCouponEntity;
import coupon.mapper.EntityDomainMapper;
import coupon.repository.UserCouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponService {

    public static final int MAX_COUNT = 5;
    private final UserCouponRepository userCouponRepository;
    private final ReaderRepository readerRepository;

    @Transactional
    @CachePut(value = "coupon", key = "#result.id")
    public UserCouponEntity create(long userId, long couponId, LocalDateTime expireDateTime) {
        validateCouponCount(userId);
        return userCouponRepository.save(new UserCouponEntity(userId, couponId, false, expireDateTime));
    }

    private void validateCouponCount(long userId) {
        List<UserCouponEntity> userCouponEntities = userCouponRepository.findAllByUserId(userId);
        if(userCouponEntities.size() > MAX_COUNT) {
            throw new IllegalArgumentException("발급받을 수 있는 쿠폰의 수를 초과했습니다.");
        }
    }

    @Cacheable(value = "coupon", key = "#id")
    @Transactional(readOnly = true)
    public List<UserCoupon> getUserCoupons(long userId) {
        List<UserCouponEntity> userCouponEntities = readerRepository.findUserCouponsByUserId(userId);
        return userCouponEntities.stream()
                .map(EntityDomainMapper::mapToUserCoupon)
                .toList();
    }
}
