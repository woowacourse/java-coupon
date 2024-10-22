package coupon.coupon.service;


import aspect.WriterTransactional;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.MemberCouponRepository;
import coupon.member.domain.Member;
import coupon.member.service.MemberService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_ORDER_AMOUNT = 100000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int MAX_COUPON_ISSUE_LIMIT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberService memberService;

    @Transactional
    public long create(Coupon coupon) {
        validateDiscountAmount(coupon.getDiscountAmount());
        validateMinOrderAmount(coupon.getMinOrderAmount());
        validateEndDate(coupon.getStartDate(), coupon.getEndDate());
        validateDiscountRate(coupon.calculateDiscountRate());
        Coupon saveCoupon = couponRepository.save(coupon);
        return saveCoupon.getId();
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 %d원 이상 %d원 이하여야 합니다.", MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT)
            );
        }
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 %d원 단위여야 합니다.", DISCOUNT_UNIT));
        }
    }

    private void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %d원 이상이어야 합니다.", MAX_ORDER_AMOUNT));
        }
        if (minOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %d원 이하여야 합니다.", MAX_ORDER_AMOUNT));
        }
    }

    private void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
        }
    }

    private void validateDiscountRate(double discountRate) {
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    String.format("할인율은 %d%% 이상 %d%% 이하여야 합니다.", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }

    @Transactional
    public void issueCoupon(long couponId, long memberId) {
        Coupon findCoupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        Member findMember = memberService.findBy(memberId);

        validateIssuance(findMember, findCoupon);

        MemberCoupon memberCoupon = new MemberCoupon(findCoupon, findMember, LocalDateTime.now());
        memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuance(Member findMember, Coupon findCoupon) {
        int issuedCouponCount = memberCouponRepository.countByMemberAndAndCoupon(findMember, findCoupon);
        if (issuedCouponCount >= MAX_COUPON_ISSUE_LIMIT) {
            throw new IllegalArgumentException(String.format("%d장 이상의 쿠폰을 발급할 수 없습니다.", MAX_COUPON_ISSUE_LIMIT));
        }
    }

    @WriterTransactional
    public Coupon findById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon이 존재하지 않습니다."));
    }
}
