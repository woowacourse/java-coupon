package coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Coupon {

	private  static final int MAX_NAME_LENGTH = 30;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int minimumOrderAmount;
	private LocalDate startAt;
	private LocalDate endAt;
	@Enumerated(EnumType.STRING)
	private Category category;
	@Embedded
	private Discount discount;

	protected Coupon() {
	}

	public Coupon(String name, int price, int minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
		validateName(name);
		this.name = name;
		this.discount = new Discount(price, minimumOrderAmount);
		this.minimumOrderAmount = minimumOrderAmount;
		this.category = category;
		this.startAt = startAt;
		this.endAt = endAt;
	}

	private void validateName(String name) {
		if(name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("이름은 30자 이내로 입력해주세요.");
		}
	}

	private void validateDate(String name) {
		if(startAt.isAfter(endAt)) {
			throw new IllegalArgumentException("시작일이 종료일 보다 늦을 수 없습니다.");
		}
	}
}
