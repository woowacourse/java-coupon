package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionTemplate transactionTemplate;

    public CouponService(CouponRepository couponRepository, PlatformTransactionManager transactionManager) {
        this.couponRepository = couponRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponOnWriter(id));
    }

    private Coupon getCouponOnWriter(Long id) {
        int originalPropagationBehavior = transactionTemplate.getPropagationBehavior();

        try {
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            return transactionTemplate.execute(status -> couponRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 쿠폰이 존재하지 않습니다.")));
        } finally {
            transactionTemplate.setPropagationBehavior(originalPropagationBehavior);
        }
    }
}
