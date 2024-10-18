package coupon.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
