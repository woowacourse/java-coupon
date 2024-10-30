package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.CouponRequest;
import coupon.dto.MemberCouponRequest;
import coupon.dto.MemberCouponResponse;
import coupon.repository.CategoryRepository;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDate;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {
    private static final int MAX_COUPON_COUNT = 5;
    private static final String COUPON_CACHE_NAME = "coupon";

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;

    public CouponService(CouponRepository couponRepository,
                         CategoryRepository categoryRepository,
                         MemberCouponRepository memberCouponRepository,
                         MemberRepository memberRepository) {
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
        this.memberCouponRepository = memberCouponRepository;
        this.memberRepository = memberRepository;
    }

    @CachePut(cacheNames = COUPON_CACHE_NAME, key = "#result.id")
    @Transactional
    public Coupon create(CouponRequest couponRequest) {
        Category category = categoryRepository.findById(couponRequest.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return couponRepository.save(couponRequest.toEntity(category));
    }

    @Transactional
    public MemberCoupon issueMemberCoupon(MemberCouponRequest request) {
        Coupon coupon = getCoupon(request.couponId());
        Member member = getMember(request.memberId());
        validate(coupon, member);
        return memberCouponRepository.save(new MemberCoupon(coupon.getId(), member.getId()));
    }

    private void validate(Coupon coupon, Member member) {
        if (!coupon.canIssue(LocalDate.now())) {
            throw new IllegalArgumentException("쿠폰을 발급할 수 있는 기간이 아닙니다.");
        }

        if (memberCouponRepository.countByCouponIdAndMemberId(coupon.getId(), member.getId()) >= MAX_COUPON_COUNT) {
            throw new IllegalArgumentException(
                    String.format("멤버당 동일한 쿠폰은 최대 %d개만 발급 가능합니다.", MAX_COUPON_COUNT));
        }
    }

    @Cacheable(cacheNames = COUPON_CACHE_NAME, key = "#couponId")
    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }

    private Member getMember(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = getCoupon(memberCoupon.getCouponId());
        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
