package coupon.marketing.repository;

import coupon.marketing.domain.MonthlyMemberBenefit;
import java.time.Month;
import java.time.Year;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyMemberBenefitRepository extends JpaRepository<MonthlyMemberBenefit, Long> {
    Optional<MonthlyMemberBenefit> findByMemberIdAndYearAndMonth(Long memberId, Year year, Month month);

    Optional<MonthlyMemberBenefit> findTopByYearAndMonthOrderByCouponDiscountAmountDesc(Year year, Month month);
}
