package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {
    private static final int MAX_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(MemberCouponRepository memberCouponRepository) {
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional
    public MemberCoupon issueCoupon(Coupon coupon, Member member) {
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

    @Transactional(readOnly = true)
    public List<MemberCoupon> getMemberCoupon(long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
