package coupon.service.membercoupon;

import coupon.entity.coupon.Coupon;
import coupon.entity.membercoupon.MemberCoupon;
import coupon.exception.membercoupon.CannotCreateMemberCouponException;
import coupon.helper.TransactionExecutor;
import coupon.repository.membercoupon.MemberCouponRepository;
import coupon.service.coupon.CouponService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_CREATE_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;
    private final TransactionExecutor transactionExecutor;

    @Transactional
    public Long create(MemberCoupon memberCoupon) {
        validate(memberCoupon);
        MemberCoupon savedMemberCoupon = memberCouponRepository.save(memberCoupon);
        return savedMemberCoupon.getId();
    }

    private void validate(MemberCoupon memberCoupon) {
        List<MemberCoupon> existingCoupons = memberCouponRepository.findByMemberIdAndCouponIdWithLock(
                memberCoupon.getMemberId(),
                memberCoupon.getCouponId()
        );
        if (existingCoupons.size() >= MAX_MEMBER_COUPON_CREATE_COUNT) {
            throw new CannotCreateMemberCouponException();
        }
    }

    /* memberCoupons 조회 로직 (복제지연 고려) -> 고민 중. 적용 안함
     * 1. 읽기 db에서 값을 읽는다
     * 2. 읽기 db에 값이 없으면 쓰기 db에서 읽는다
     * 여기서, 목록 조회기 때문에 읽기 db에 값이 없다는 걸 검증하기 애매함
     * */
    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId).stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.getCouponFromCache(memberCoupon.getCouponId());
        return new MemberCouponResponse(memberCoupon, coupon);
    }
}
