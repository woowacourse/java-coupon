package coupon.domain.membercoupon.repository;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.exception.CouponNotFoundException;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.exception.MemberNotFoundException;
import coupon.domain.member.repository.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.infra.db.MemberCouponEntity;
import coupon.infra.db.jpa.JpaMemberCouponRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberCouponRepositoryAdaptor implements
        MemberCouponRepository {

    private final CouponRepository couponRepository;

    private final MemberRepository memberRepository;

    private final JpaMemberCouponRepository memberCouponRepository;


    @Override
    public List<MemberCoupon> findAllByMemberIdAndCouponId(Long memberId, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("해당 쿠폰이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));

        List<MemberCouponEntity> memberCouponEntities = memberCouponRepository.findAllByMemberIdAndCouponId(
                memberId,
                couponId
        );

        return memberCouponEntities.stream()
                .map(entity -> MemberCoupon.from(entity, coupon, member))
                .toList();
    }

    @Override
    public List<MemberCoupon> findAllByMemberId(Long memberId) {
        return List.of();
    }

    @Override
    public Optional<MemberCoupon> save(MemberCoupon memberCoupon) {
        return Optional.empty();
    }
}
