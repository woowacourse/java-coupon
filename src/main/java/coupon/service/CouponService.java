package coupon.service;

import coupon.config.DataSourceRouter;
import coupon.config.DataSourceType;
import coupon.domain.coupon.Coupon;
import coupon.entity.CouponEntity;
import coupon.mapper.DomainEntityMapper;
import coupon.mapper.EntityDomainMapper;
import coupon.repository.CouponRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponEntity create(Coupon coupon) {
        return couponRepository.save(DomainEntityMapper.mapToCouponEntity(coupon));
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        try {
            Optional<CouponEntity> couponEntity = getCouponById(id);
            if (couponEntity.isEmpty()) {
                DataSourceRouter.setDataSourceType(DataSourceType.WRITER);
                couponEntity = couponRepository.findById(id);
            }
            if(couponEntity.isEmpty()) {
                throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
            }
            return EntityDomainMapper.mapToCoupon(couponEntity.get());
        } finally {
            DataSourceRouter.clearDataSourceType();
        }
    }

    private Optional<CouponEntity> getCouponById(Long id) {
        try {
            return couponRepository.findById(id);
        } catch (SQLGrammarException ex) {
            log.info("쿠폰이 조회되지 않습니다. - id: {}", id);
            return Optional.empty();
        }
    }
}
