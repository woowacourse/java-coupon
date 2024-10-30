package coupon.service.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.coupon.MemberCoupon;
import coupon.domain.coupon.MemberCouponRepository;
import coupon.exception.CouponException;
import coupon.service.coupon.dto.MemberCouponsResponse;
import coupon.support.TransactionSupport;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CouponService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final TransactionSupport transactionSupport;

    @Transactional
    @CachePut(value = "coupon", key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#id")
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponWriter(id));
    }

    private Coupon getCouponWriter(Long id) {
        return transactionSupport.executeNewTransaction(
                () -> couponRepository.findById(id).orElseThrow(() -> new CouponException("존재하지 않는 쿠폰입니다."))
        );
    }

    @Transactional
    public MemberCoupon issue(Long memberId, Long couponId) {
        LocalDateTime issuedAt = LocalDateTime.now();
        Coupon coupon = getCouponWriter(couponId);

        validateIssueCount(memberId, couponId);

        MemberCoupon memberCoupon = new MemberCoupon(memberId, coupon, issuedAt);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateIssueCount(Long memberId, Long couponId) {
        int issueCount = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
        if (issueCount >= MAX_ISSUE_COUNT) {
            throw new CouponException("동일한 쿠폰은 최대 %d장까지 발급할 수 있습니다.".formatted(MAX_ISSUE_COUNT));
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponsResponse> getMemberCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        if (memberCoupons.isEmpty()) {
            memberCoupons = getMemberCouponWriter(memberId);
        }

        Map<Long, Coupon> coupons = getCoupons(memberCoupons);
        return memberCoupons.stream()
                .map(memberCoupon -> MemberCouponsResponse.of(coupons.get(memberCoupon.getCouponId()), memberCoupon))
                .toList();
    }

    private List<MemberCoupon> getMemberCouponWriter(Long memberId) {
        return transactionSupport.executeNewTransaction(() -> memberCouponRepository.findAllByMemberId(memberId));
    }

    private Map<Long, Coupon> getCoupons(List<MemberCoupon> memberCoupons) {
        return memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .distinct()
                .map(this::getCoupon)
                .collect(Collectors.toMap(Coupon::getId, Function.identity()));
    }
}
