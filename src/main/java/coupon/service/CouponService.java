package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.entity.CouponEntity;
import coupon.mapper.DomainEntityMapper;
import coupon.mapper.EntityDomainMapper;
import coupon.repository.CouponReaderRepository;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponReaderRepository couponReaderRepository;

    @Transactional
    public CouponEntity create(Coupon coupon) {
        return couponRepository.save(DomainEntityMapper.mapToCouponEntity(coupon));
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        CouponEntity couponEntity = couponReaderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
        return EntityDomainMapper.mapToCoupon(couponEntity);
    }
}
