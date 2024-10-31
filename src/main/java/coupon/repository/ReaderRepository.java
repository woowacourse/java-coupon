package coupon.repository;

import coupon.entity.CouponEntity;
import coupon.entity.UserCouponEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ReaderRepository {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final WriterRepository writerRepository;

    @Transactional(readOnly = true)
    public Optional<CouponEntity> findById(long id) {
        Optional<CouponEntity> fromReader = couponRepository.findById(id);
        if (fromReader.isPresent()) {
            return fromReader;
        }
        return writerRepository.findCouponById(id);
    }

    @Transactional(readOnly = true)
    public List<UserCouponEntity> findUserCouponsByUserId(long userId) {
        List<UserCouponEntity> fromReader = userCouponRepository.findAllByUserId(userId);
        if (!fromReader.isEmpty()) {
            return fromReader;
        }
        return writerRepository.findUserCouponsByUserId(userId);
    }
}
