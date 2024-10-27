package coupon.service.reader;

import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponReaderService {

    private static final Logger log = LoggerFactory.getLogger(MemberCouponReaderService.class);

    private final MemberCouponRepository memberCouponRepository;

    @Transactional(readOnly = true)
    public MemberCoupon getMemberCoupon(Long id, Supplier<MemberCoupon> memberCouponWriter) {
        log.info("Find member coupon by id: {}", id);

        return memberCouponRepository.findById(id)
                .orElseGet(memberCouponWriter);
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> getIssuedMemberCoupon(Long memberId, Supplier<List<MemberCoupon>> memberCouponWriter) {
        log.info("Find issued member coupons by member id: {}", memberId);

        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
