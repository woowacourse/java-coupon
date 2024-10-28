package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponRequest;
import coupon.service.reader.MemberCouponReaderService;
import coupon.service.writer.MemberCouponWriterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    public static final int MAXIMUM_ISSUABLE_COUPONS_COUNT = 5;
    private final MemberCouponReaderService readerService;
    private final MemberCouponWriterService writerService;

    @Transactional(readOnly = true)
    public List<MemberCoupon> getIssuedMemberCoupons(Long memberId) {
        return readerService.getIssuedMemberCoupon(memberId, () -> writerService.getIssuedCouponsWithWriter(memberId));
    }

    @Transactional
    public MemberCoupon issueCoupon(MemberCouponRequest memberCouponRequest) {
        validateIssuable(memberCouponRequest.memberId());
        return writerService.create(memberCouponRequest.toMemberCoupon());
    }

    private void validateIssuable(Long memberId) {
        if (readerService.countMemberCoupon(memberId) > MAXIMUM_ISSUABLE_COUPONS_COUNT) {
            throw new IllegalArgumentException("해당 사용자에게 발행할 수 있는 쿠폰의 최대 개수를 초과했습니다.");
        }
    }
}
