package coupon.coupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.coupon.persistence.MemberCouponReader;
import coupon.coupon.persistence.MemberCouponWriter;
import coupon.member.domain.Member;
import coupon.member.persistence.MemberReader;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int MAX_ISSUE_COUNT = 5;
    public static final String MEMBER_COUPON_CACHE_NAME = "memberCoupons";

    private final CouponService couponService;
    private final MemberReader memberReader;
    private final MemberCouponReader memberCouponReader;
    private final MemberCouponWriter memberCouponWriter;
    private final TransactionRouter transactionRouter;

    @Transactional
    @CacheEvict(cacheNames = MEMBER_COUPON_CACHE_NAME, key = "#memberId")
    public MemberCoupon issueCoupon(long memberId, long couponId) {
        Member member = memberReader.getMember(memberId);
        validateIssueCount(memberId, couponId);
        Coupon coupon = couponService.getCoupon(couponId);
        validateUsableCoupon(coupon);
        return memberCouponWriter.create(new MemberCoupon(member.getId(), coupon.getId()));
    }

    private void validateUsableCoupon(Coupon coupon) {
        if (!coupon.usable()) {
            throw new IllegalArgumentException(
                    String.format("해당 쿠폰은 %s ~ %s 기간 안에만 사용가능합니다.",
                            DATE_TIME_FORMATTER.format(coupon.getIssuePeriod().getStartAt()),
                            DATE_TIME_FORMATTER.format(coupon.getIssuePeriod().getEndAt())
                    ));
        }
    }

    private void validateIssueCount(long memberId, long couponId) {
        if (memberCouponReader.countByMemberIdAndCouponId(memberId, couponId) >= MAX_ISSUE_COUNT) {
            throw new IllegalArgumentException(String.format("회원당 최대 %d개의 쿠폰만 발급 가능합니다.", MAX_ISSUE_COUNT));
        }
    }

    @Cacheable(cacheNames = MEMBER_COUPON_CACHE_NAME, key = "#memberId", unless = "#result.isEmpty()")
    public List<MemberCouponResponse> findAllByMemberId(long memberId) {
        List<MemberCoupon> memberCoupons = transactionRouter.route(() -> memberCouponReader.findAllByMemberId(memberId));
        return memberCoupons.stream()
                .map(memberCoupon ->
                        MemberCouponResponse.of(memberCoupon, couponService.getCoupon(memberCoupon.getCouponId()))
                )
                .toList();
    }
}
