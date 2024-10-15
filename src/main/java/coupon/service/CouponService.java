package coupon.service;

import coupon.CouponException;
import coupon.dto.CouponCreateRequest;
import coupon.entity.Coupon;
import coupon.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public void create(CouponCreateRequest request) {
        couponRepository.save(new Coupon(
                request.name(),
                request.discount(),
                request.minimumOrder(),
                request.category(),
                request.start(),
                request.end()
        ));
    }

    public Coupon read(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("coupon with id " + id + " not found"));
    }
}
