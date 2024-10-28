package coupon.coupon.service;

import coupon.coupon.dto.CouponResponse;
import coupon.coupon.dto.MemberCouponRequest;
import coupon.coupon.entity.MemberCouponEntity;
import coupon.coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    public MemberCouponEntity create(MemberCouponRequest memberCouponRequest) {
        validateIssueDate(memberCouponRequest);
        List<MemberCouponEntity> memberCouponEntities = memberCouponRepository.findByCouponIdAndMemberId(memberCouponRequest.couponId(), memberCouponRequest.memberId());
        validateMemberCouponSize(memberCouponEntities);
        MemberCouponEntity memberCouponEntity = new MemberCouponEntity(memberCouponRequest.memberId(), memberCouponRequest.couponId(), false, memberCouponRequest.issueDate());
        return memberCouponRepository.save(memberCouponEntity);
    }

    private void validateIssueDate(MemberCouponRequest memberCouponRequest) {
        CouponResponse couponResponse = couponService.getCoupon(memberCouponRequest.couponId());
        LocalDate issueDate = memberCouponRequest.issueDate();
        if (issueDate.isBefore(couponResponse.startDate()) || issueDate.isAfter(couponResponse.endDate())) {
            throw new IllegalArgumentException("발급 기간이 유효하지 않습니다");
        }
    }

    private void validateMemberCouponSize(List<MemberCouponEntity> memberCouponEntities) {
        if (memberCouponEntities.size() >= 5) {
            throw new IllegalArgumentException("최대 5장까지만 발급가능합니다");
        }
    }

    public List<MemberCouponEntity> findByMemberId(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId);
    }
}
