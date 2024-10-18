package coupon.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.service.dto.request.CouponCreateRequest;
import coupon.service.port.CouponRepository;
import coupon.util.TransactionExecLogic;
import coupon.util.TransactionExecutor;

@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionExecutor<Coupon> transactionExecutor;

    public CouponService(final CouponRepository couponRepository, final TransactionExecutor<Coupon> transactionExecutor) {
        this.couponRepository = couponRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Transactional
    public void createCoupon(final CouponCreateRequest request) {
        final Coupon createdCoupon = Coupon.create(
                request.name(),
                request.minimumOrderAmount(),
                request.discountAmount(),
                request.productionCategory(),
                request.couponStartDate(),
                request.couponEndDate()
        );
        couponRepository.save(createdCoupon);
    }

    public Coupon findByName(final String name) {
        final CouponName couponName = new CouponName(name);
        return couponRepository.findByName(couponName)
                .orElseGet(() -> findByNameFromWriterDb(couponName));
    }

    private Coupon findByNameFromWriterDb(final CouponName couponName) {
        final TransactionExecLogic<Coupon> logic = () -> couponRepository.findByName(couponName)
                .orElseThrow(() -> new NoSuchElementException("입력된 쿠폰 이름에 일치하는 쿠폰 정보가 존재하지 않습니다. -" + couponName.getValue()));
        return transactionExecutor.exec(logic);
    }
}
