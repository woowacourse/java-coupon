package coupon.coupon.service;

import coupon.coupon.domain.ExceptionMessage;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.dto.MemberCouponRequest;
import coupon.coupon.entity.MemberCouponEntity;
import coupon.coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    static final int MAX_MEMBER_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    @Transactional
    public MemberCouponEntity create(MemberCouponRequest memberCouponRequest) {
        validateIssueDate(memberCouponRequest);
        validateMemberCouponSize(memberCouponRequest);
        MemberCouponEntity memberCouponEntity = new MemberCouponEntity(memberCouponRequest.couponId(),
                memberCouponRequest.memberId(), false, memberCouponRequest.issueDate());
        return memberCouponRepository.save(memberCouponEntity);
    }

    private void validateIssueDate(MemberCouponRequest memberCouponRequest) {
        CouponResponse couponResponse = couponService.getCoupon(memberCouponRequest.couponId());
        LocalDate issueDate = memberCouponRequest.issueDate();
        if (issueDate.isBefore(couponResponse.startDate()) || issueDate.isAfter(couponResponse.endDate())) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ISSUE_DATE.getMessage());
        }
    }

    private void validateMemberCouponSize(MemberCouponRequest memberCouponRequest) {
        int memberCouponCount = memberCouponRepository.countByCouponIdAndMemberId(
                memberCouponRequest.couponId(), memberCouponRequest.memberId());
        if (memberCouponCount >= MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException(String.format(ExceptionMessage.OVER_FIVE_COUPON.getMessage(),
                    MAX_MEMBER_COUPON_COUNT));
        }
    }

    @Transactional
    public List<MemberCouponEntity> findByMemberId(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId);
    }
}
