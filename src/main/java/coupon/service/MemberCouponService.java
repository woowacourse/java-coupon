package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.MemberCouponRepository;
import coupon.service.dto.MemberCouponResponse;
import coupon.util.TransactionExecutor;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberCouponService {

    private static final int MAX_SAME_COUPON_SIZE = 5;

    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(CouponService couponService, MemberCouponRepository memberCouponRepository) {
        this.couponService = couponService;
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional
    public MemberCoupon create(Member member, Coupon coupon) {
        int sameCouponSize = memberCouponRepository.countMemberCouponByMemberIdAndCouponId(member.getId(), coupon.getId());
        if (sameCouponSize > MAX_SAME_COUPON_SIZE) {
            throw new IllegalArgumentException("Same coupon size exceeded");
        }
        MemberCoupon issue = MemberCoupon.issue(member.getId(), coupon.getId(), coupon.getIssueStartDate());
        return memberCouponRepository.save(issue);
    }

    public List<MemberCouponResponse> findAllByMemberId(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.getById(memberCoupon.getCouponId());
        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
