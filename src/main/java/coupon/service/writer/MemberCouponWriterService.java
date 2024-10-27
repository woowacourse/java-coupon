package coupon.service.writer;

import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponWriterService {

    private static final Logger log = LoggerFactory.getLogger(MemberCouponWriterService.class);

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon create(MemberCoupon coupon) {
        return memberCouponRepository.save(coupon);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<MemberCoupon> getIssuedCouponsWithWriter(Long memberId) {
        log.info("Find member coupon by memberId: {}", memberId);

        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
