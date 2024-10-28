package coupon.repository;

import coupon.entity.CouponEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CouponReaderRepository {

    private final CouponRepository couponRepository;
    private final CouponWriterRepository couponWriterRepository;

    @Transactional(readOnly = true)
    public Optional<CouponEntity> findById(long id) {
        Optional<CouponEntity> fromReader = couponRepository.findById(id);
        if (fromReader.isPresent()) {
            return fromReader;
        }
        return couponWriterRepository.readFromWriter(id);
    }
}
