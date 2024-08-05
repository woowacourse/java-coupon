package coupon.marketing.service;

import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.repository.MemberCouponRepository;
import coupon.coupon.service.observer.MemberCouponObserverAdapter;
import coupon.marketing.domain.MonthlyMemberBenefit;
import coupon.marketing.repository.MonthlyMemberBenefitRepository;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class MarketingService extends MemberCouponObserverAdapter {

    private final MonthlyMemberBenefitRepository monthlyMemberBenefitRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MarketingService(MonthlyMemberBenefitRepository monthlyMemberBenefitRepository,
                            MemberCouponRepository memberCouponRepository) {
        this.monthlyMemberBenefitRepository = monthlyMemberBenefitRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    @Override
    @Async
    @Transactional
    public void onUse(Long memberCouponId) {
        log.info("[쿠폰 사용 이벤트] memberCouponId: {}", memberCouponId);

        MemberCoupon memberCoupon = memberCouponRepository.findById(memberCouponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 쿠폰입니다. memberCouponId: " + memberCouponId));
        if (!memberCoupon.isUsed()) {
            log.info("[쿠폰 사용 이벤트] 사용한 쿠폰이 아닙니다. memberCouponId: {}", memberCouponId);
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        Year usedYear = Year.of(now.getYear());
        Month usedMonth = now.getMonth();

        MonthlyMemberBenefit monthlyMemberBenefit = monthlyMemberBenefitRepository.findByMemberIdAndYearAndMonth(
                        memberCoupon.getMemberId(), usedYear, usedMonth)
                .orElse(MonthlyMemberBenefit.create(memberCoupon.getMemberId(), usedYear, usedMonth));
        monthlyMemberBenefit.increaseCouponDiscountAmount(memberCoupon.getCoupon().getDiscountAmount());
        monthlyMemberBenefitRepository.save(monthlyMemberBenefit);

        log.info("[쿠폰 사용 이벤트] 쿠폰 사용 금액을 반영했습니다. memberCouponId: {}", memberCouponId);
    }

    @Transactional(readOnly = true)
    public MonthlyMemberBenefit findMaxCouponDiscountAmountMemberByMonth(Year year, Month month) {
        return monthlyMemberBenefitRepository.findTopByYearAndMonthOrderByCouponDiscountAmountDesc(year, month)
                .orElseThrow(() -> new IllegalArgumentException("해당 월에 쿠폰을 사용한 회원 정보가 없습니다."));
    }
}
