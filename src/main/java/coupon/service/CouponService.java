package coupon.service;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.exception.NotFoundCouponException;
import coupon.repository.CouponRepository;
import coupon.repository.entity.CouponEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponService {

    public static final int MAX_READ_ATTEMPT = 2;
    private final CouponRepository couponRepository;

    @Transactional
    public CouponEntity createCoupon(final Coupon request) {
        return couponRepository.save(CouponEntity.toEntity(request));
    }

    @Transactional(readOnly = true)
    public Coupon getCouponForMember(final long id) {
        int attempts = 0;
        while (attempts < MAX_READ_ATTEMPT) {
            try {
                final CouponEntity couponEntity = couponRepository.findById(id)
                        .orElseThrow(() -> new NotFoundCouponException("존재하지 않는 쿠폰입니다."));
                return couponEntity.toDomain();
            } catch (NotFoundCouponException e) {
                attempts++;
                sleep();
            }
        }
        throw new NotFoundCouponException("쿠폰을 찾을 수 없습니다.");
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("쿠폰 조회 대기 중 예외가 발생했습니다.", ie);
        }
    }

    @Transactional(readOnly = true)
    public Coupon getCouponForAdmin(final long id) {
        final CouponEntity couponEntity = couponRepository.findById(id)
                .orElseGet(() -> findCouponEntity(id));
        return couponEntity.toDomain();
    }

    @Transactional(propagation = REQUIRES_NEW)
    protected CouponEntity findCouponEntity(final long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NotFoundCouponException("쿠폰을 찾을 수 없습니다."));
    }
}
