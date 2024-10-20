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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberCouponRepositoryAdaptor implements
        MemberCouponRepository {

    private final CouponRepository couponRepository;

    private final MemberRepository memberRepository;

    private final JpaMemberCouponRepository jpaMemberCouponRepository;


    @Override
    public List<MemberCoupon> findAllByMemberIdAndCouponId(Long memberId, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("해당 쿠폰이 존재하지 않습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));

        List<MemberCouponEntity> memberCouponEntities = jpaMemberCouponRepository.findAllByMemberIdAndCouponId(
                memberId,
                couponId
        );

        return memberCouponEntities.stream()
                .map(entity -> MemberCoupon.of(entity, coupon, member))
                .toList();
    }

    @Override
    public List<MemberCoupon> findAllByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));

        List<MemberCouponEntity> memberCouponEntities = jpaMemberCouponRepository.findAllByMemberId(memberId);
        Map<Long, Coupon> couponMap = findIdCouponMap(memberCouponEntities);

        return memberCouponEntities.stream().map(memberCouponEntity -> {
            Long couponId = memberCouponEntity.getCouponId();
            Coupon coupon = couponMap.get(couponId);
            return MemberCoupon.of(memberCouponEntity, coupon, member);
        }).toList();
    }

    private Map<Long, Coupon> findIdCouponMap(List<MemberCouponEntity> memberCouponEntities) {
        List<Long> couponIds = memberCouponEntities.stream().map(MemberCouponEntity::getCouponId).toList();
        return couponRepository.findAllByIdIn(couponIds).stream()
                .collect(Collectors.toMap(Coupon::getId, coupon -> coupon));
    }

    @Override
    public Optional<MemberCoupon> save(MemberCoupon memberCoupon) {
        return Optional.empty();
    }
}
