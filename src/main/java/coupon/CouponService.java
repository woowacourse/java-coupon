package coupon;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);

        Coupon readCoupon = myFunc(coupon);

        System.out.println(savedCoupon.getId() + " " + readCoupon.getId());

        if (!savedCoupon.equals(readCoupon)) {
            throw new RuntimeException();
        }

    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Coupon myFunc(Coupon coupon) {
        System.out.println(TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        return couponRepository.findById(coupon.getId()).get();
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Coupon> getCoupons() {
        return couponRepository.findAll();
    }
}
