package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.CouponRequest;
import coupon.global.ReplicationLagFallback;
import coupon.repository.CategoryRepository;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {
    private static final int MAX_COUPON_COUNT = 5;

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final MemberCouponRepository memberCouponRepository;

    private final ReplicationLagFallback replicationLagFallback;

    public CouponService(CouponRepository couponRepository,
                         CategoryRepository categoryRepository,
                         MemberCouponRepository memberCouponRepository,
                         ReplicationLagFallback replicationLagFallback) {
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
        this.memberCouponRepository = memberCouponRepository;
        this.replicationLagFallback = replicationLagFallback;
    }

    @Transactional
    public Coupon create(CouponRequest couponRequest) {
        Category category = categoryRepository.findById(couponRequest.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return couponRepository.save(couponRequest.toEntity(category));
    }

    @Transactional
    public MemberCoupon issueCoupon(Coupon coupon, Member member) {
        validate(coupon, member);
        return memberCouponRepository.save(new MemberCoupon(coupon, member));
    }

    private void validate(Coupon coupon, Member member) {
        if (!coupon.canIssue()) {
            throw new IllegalArgumentException("쿠폰을 발급할 수 있는 기간이 아닙니다.");
        }

        if (memberCouponRepository.countByCouponAndMember(coupon, member) >= MAX_COUPON_COUNT) {
            throw new IllegalArgumentException(
                    String.format("멤버당 동일한 쿠폰은 최대 %d개만 발급 가능합니다.", MAX_COUPON_COUNT));
        }
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> replicationLagFallback.readFromWriter(() -> findById(couponId)));
    }

    private Coupon findById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }
}
