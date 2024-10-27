package coupon.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.utill.WriterDbReader;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private static final int ISSUE_LIMIT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final WriterDbReader writerDbReader;

    @Transactional
    public MemberCoupon issue(Member member, Coupon coupon) {
        List<MemberCoupon> issuedMemberCoupons = memberCouponRepository.findAllByMemberAndCoupon(member, coupon);
        if (issuedMemberCoupons.size() >= ISSUE_LIMIT) {
            throw new GlobalCustomException(ErrorMessage.EXCEED_ISSUE_MEMBER_COUPON);
        }
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false);
        return memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> findAllIssuedCoupons(Member member) {
        List<MemberCoupon> issuedCoupons = writerDbReader.read(
                () -> memberCouponRepository.findAllByMember(member)); // 쿠폰을 발급하자마자 조회할 것을 대비
        List<Long> couponIds = issuedCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();

        List<Coupon> coupons = couponRepository.findAllByIdIn(couponIds);
        Map<Long, Coupon> couponsById = coupons.stream()
                .collect(toMap(Coupon::getId, identity()));

        issuedCoupons.forEach(memberCoupon -> {
            Coupon coupon = couponsById.get(memberCoupon.getCouponId());
            memberCoupon.loadCoupon(coupon);
        });
        return issuedCoupons;
    }
}
