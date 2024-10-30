package coupon.config;

import coupon.data.CouponEntity;
import coupon.data.repository.CouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheInitializr implements ApplicationRunner {

    private final CouponRepository couponRepository;

    @Override
    public void run(ApplicationArguments args) {
        List<CouponEntity> couponEntities = couponRepository.findAll();
        couponEntities.forEach(CouponCache::cache);
    }
}
