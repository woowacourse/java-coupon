package coupon.service;

import coupon.data.repository.CouponRepository;
import coupon.data.Coupon;
import coupon.data.repository.MemberCouponRepository;
import coupon.domain.coupon.CouponMapper;
import coupon.domain.member.MemberCoupon;
import coupon.domain.member.MemberCouponMapper;
import coupon.exception.CouponNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon create(coupon.domain.coupon.Coupon coupon) {
        return couponRepository.save(CouponMapper.toEntity(coupon));
    }

    @Transactional(readOnly = true)
    public Coupon findCoupon(long id) {
        return couponRepository.findById(id).orElseThrow(() -> new CouponNotFoundException(String.valueOf(id)));
    }

    @Transactional(readOnly = true)
    public List<Coupon> findCoupons() {
        return couponRepository.findAll();
    }
}
