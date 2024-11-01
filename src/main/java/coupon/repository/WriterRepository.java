package coupon.repository;

import coupon.entity.CouponEntity;
import coupon.entity.UserCouponEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WriterRepository {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<CouponEntity> findCouponById(long id) {
        return couponRepository.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<UserCouponEntity> findUserCouponsByUserId(long userId) {
        return userCouponRepository.findAllByUserId(userId);
    }
}
