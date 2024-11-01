package member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Entity
public class MemberCoupon {

	public static final int USEABLE_DAYS = 7;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private long couponId;
	private long memberId;
	private boolean used;
	private LocalDateTime issuedAt;
	private LocalDateTime expiredAt;

	protected MemberCoupon() {
	}

	public MemberCoupon(long couponId, long memberId) {
		this.couponId = couponId;
		this.memberId = memberId;
		this.used = false;
		this.issuedAt = LocalDateTime.now();
		this.expiredAt = LocalDateTime.of(
			issuedAt.plusDays(USEABLE_DAYS).toLocalDate(),
			LocalTime.MIN
		);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MemberCoupon that = (MemberCoupon) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
