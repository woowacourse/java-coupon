package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

//    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseThrow();
    }
    /*
    1. 쿠폰 생성, 조회 기능 구현
쿠폰의 제약조건은 DB 복제와 캐시 페이지의 미션 설명을 참고한다.
조회 기능은 부하 분산을 위해 reader DB의 데이터를 조회해야 한다.
     */
}
