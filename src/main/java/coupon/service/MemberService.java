package coupon.service;

import coupon.dao.MemberCouponCacheDao;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.exception.BadRequestException;
import coupon.exception.NotFoundException;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import coupon.util.TransactionExecutor;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private final CouponService couponService;
    private final TransactionExecutor transactionExecutor;

    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponCacheDao memberCouponCacheDao;

    public Member create(String name) {
        return memberRepository.save(new Member(name));
    }

    public List<MemberCouponResponse> getIssuedCoupons(long memberId) {
        return memberCouponRepository.findByMemberId(memberId).stream()
                .map(this::parseToResponse)
                .toList();
    }

    private MemberCouponResponse parseToResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.getCoupon(memberCoupon.getCouponId())
                .orElseThrow(() -> new NotFoundException("Coupon not found: " + memberCoupon.getCouponId()));
        return MemberCouponResponse.from(memberCoupon, coupon);
    }

    @Transactional
    public MemberCoupon issueCoupon(long memberId, long couponId) {
        Optional<Coupon> coupon = couponService.getCoupon(couponId);
        if (coupon.isEmpty()) {
            throw new NotFoundException("Coupon not found: " + couponId);
        }
        validateIssueAvailable(coupon.get());

        MemberCoupon memberCoupon = memberCouponRepository.save(new MemberCoupon(memberId, couponId));
        validateCountWithIncrement(memberId, couponId);

        return memberCoupon;
    }

    private void validateIssueAvailable(Coupon coupon) {
        if (!coupon.issueAvailable()) {
            throw new BadRequestException("Coupon could not be issued: (" +
                    coupon.getIssuedStartDate() + " ~ " +
                    coupon.getIssuedEndDate() + ")");
        }
    }

    private void validateCountWithIncrement(long memberId, long couponId) {
        cacheCountIfNotExistKey(memberId, couponId);

        Long incrementCount = memberCouponCacheDao.incrementCount(memberId, couponId);
        if (incrementCount > MAX_MEMBER_COUPON_COUNT) {
            memberCouponCacheDao.setCount(memberId, couponId, MAX_MEMBER_COUPON_COUNT);
            throw new BadRequestException("Exceeded the number of coupons can be issued ");
        }
    }

    private void cacheCountIfNotExistKey(long memberId, long couponId) {
        if (!memberCouponCacheDao.existKey(memberId, couponId)) {
            long couponCount = transactionExecutor.executeOnWriter(() ->
                    memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId));
            memberCouponCacheDao.setCount(memberId, couponId, couponCount);
        }
    }
}
