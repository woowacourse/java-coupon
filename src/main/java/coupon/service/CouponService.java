package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.service.dto.MemberCouponDto;
import coupon.service.exception.CouponBusinessLogicException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private static final long MAX_SAME_COUPON = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public List<MemberCouponDto> getIssuedCouponsByMemberId(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId).stream()
                .map(this::toDto)
                .toList();
    }

    private MemberCouponDto toDto(MemberCoupon memberCoupon) {
        return MemberCouponDto.from(getCoupon(memberCoupon.getCouponId()), memberCoupon);
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public void issueCoupon(Long memberId, Long couponId) {
        if (memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId) >= MAX_SAME_COUPON) {
            throw new CouponBusinessLogicException("The coupon can no longer be issued to that user");
        }

        MemberCoupon issuedCoupon = new MemberCoupon(memberId, getCoupon(couponId), LocalDateTime.now());
        memberCouponRepository.save(issuedCoupon);
    }
}
