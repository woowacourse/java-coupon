package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.entity.CouponEntity;
import coupon.mapper.DomainEntityMapper;
import coupon.mapper.EntityDomainMapper;
import coupon.repository.ReaderRepository;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final ReaderRepository readerRepository;

    @Transactional
    public CouponEntity create(Coupon coupon) {
        return couponRepository.save(DomainEntityMapper.mapToCouponEntity(coupon));
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        CouponEntity couponEntity = readerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
        return EntityDomainMapper.mapToCoupon(couponEntity);
    }
}
