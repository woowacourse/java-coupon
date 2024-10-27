package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponWriterService {

	private final CouponRepository couponRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Coupon findCouponInWriter(long id) {
		return couponRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id = " + id));
	}
}
