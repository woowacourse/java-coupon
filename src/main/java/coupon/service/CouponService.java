package coupon.service;

import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import coupon.service.reader.CouponReaderService;
import coupon.service.writer.CouponWriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponReaderService readerService;
    private final CouponWriterService writerService;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return readerService.getCoupon(id, () -> writerService.getCouponWithWriter(id));
    }

    @Transactional
    public Coupon createCoupon(CouponRequest couponRequest) {
        return writerService.create(couponRequest.toCoupon());
    }
}
