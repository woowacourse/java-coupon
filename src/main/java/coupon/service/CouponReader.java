package coupon.service;

import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import coupon.service.support.DataAccessSupporter;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CouponReader {

    private final CouponRepository couponRepository;
    private final DataAccessSupporter dataAccessSupporter;

    @Transactional(readOnly = true)
    public CouponEntity findById(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> dataAccessSupporter.executeWriteDataBase(() -> couponRepository.findById(id))
                        .orElseThrow(NoSuchElementException::new));
    }
}
