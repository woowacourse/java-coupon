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

    private final MemberCouponReaderService readerService;
    private final MemberCouponWriterService writerService;

    @Transactional(readOnly = true)
    public List<MemberCoupon> getIssuedMemberCoupons(Long memberId) {
        return readerService.getIssuedMemberCoupon(memberId, () -> writerService.getIssuedCouponsWithWriter(memberId));
    }

    @Transactional
    public MemberCoupon issueCoupon(MemberCouponRequest memberCouponRequest) {
        return writerService.create(memberCouponRequest.toMemberCoupon());
    }
}
