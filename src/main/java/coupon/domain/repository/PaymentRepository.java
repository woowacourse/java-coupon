package coupon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
