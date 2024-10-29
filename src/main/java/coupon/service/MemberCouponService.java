package coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon issueMemberCoupon(Member member, Coupon coupon) {
        int memberCouponCount = memberCouponRepository.countByMember(member);
        if (memberCouponCount >= 5) {
            throw new IllegalArgumentException("동일한 쿠폰은 5장까지만 발급받을 수 있습니다.");
        }
        return memberCouponRepository.save(new MemberCoupon(member, coupon));
    }

    public List<Coupon> findAllCouponByMember(Member member) {
        return memberCouponRepository.findAllByMember(member).stream()
                .map(MemberCoupon::getCoupon)
                .toList();
    }
}
