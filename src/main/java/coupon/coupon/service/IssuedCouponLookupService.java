package coupon.coupon.service;

import coupon.coupon.repository.IssuedCouponEntity;
import coupon.coupon.repository.IssuedCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IssuedCouponLookupService {

    private final IssuedCouponRepository issuedCouponRepository;

    public List<IssuedCouponEntity> getAllIssuedCouponForMember(long memberId) {
        return issuedCouponRepository.findAllIssuedCouponByMemberId(memberId)
                .stream()
                .toList();
    }
}
