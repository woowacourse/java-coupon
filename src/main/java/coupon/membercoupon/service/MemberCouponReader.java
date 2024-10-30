package coupon.membercoupon.service;

import coupon.coupon.service.CouponReader;
import coupon.member.repository.MemberEntity;
import coupon.membercoupon.repository.MemberCouponEntity;
import coupon.membercoupon.repository.MemberCouponRepository;
import coupon.membercoupon.response.MemberCouponResponse;
import coupon.support.DataAccessSupporter;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MemberCouponReader {

    private final MemberCouponRepository memberCouponRepository;
    private final DataAccessSupporter dataAccessSupporter;
    private final CouponReader couponReader;

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> findByMember(MemberEntity memberEntity) {
        List<MemberCouponEntity> memberCouponEntities = dataAccessSupporter.executeWriteDataBase(
                () -> memberCouponRepository.findByMemberEntity(memberEntity));
        return memberCouponEntities.stream()
                .map(memberCoupon ->
                        new MemberCouponResponse(couponReader.findById(memberCoupon.getCouponId()), memberCoupon))
                .toList();
    }


}
