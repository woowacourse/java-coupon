package coupon.repository;

import coupon.entity.CouponEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CouponWriterRepository {

    private final CouponRepository couponRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Optional<CouponEntity> readFromWriter(long id) {
        return couponRepository.findById(id);
    }
}
