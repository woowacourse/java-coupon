package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.request.SaveCouponRequest;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponCommandService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @CachePut(value = "coupon", key = "#result.id")
    public Coupon save(SaveCouponRequest request) {
        return couponRepository.save(new Coupon(
                request.name(),
                request.discountMoney(),
                request.minimumOrderMoney(),
                request.sinceDate(),
                request.untilDate(),
                request.category()));
    }

    @CachePut(value = "memberCoupons", key = "#member.id")
    public List<MemberCoupon> issue(Member member, Coupon coupon) {
        if (memberCouponRepository.countByMemberIdAndCouponId(member.getId(), coupon.getId()) >= MAX_ISSUE_COUNT) {
            throw new CouponException("동일한 쿠폰은 최대 %d장까지 발행할 수 있습니다.".formatted(MAX_ISSUE_COUNT));
        }

        memberCouponRepository.save(new MemberCoupon(member, coupon));
        return memberCouponRepository.findAllByMemberId(member.getId());
    }
}
