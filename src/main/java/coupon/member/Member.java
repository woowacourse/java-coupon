package coupon.member;

import coupon.coupon.exception.CouponApplicationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    public Member(final Long id, final String name) {
        validateName(name);
        this.id = id;
        this.name = name;
    }

    public Member(final String name) {
        this(null, name);
    }

    private void validateName(final String name) {
        validateNameNotBlank(name);
        validateNameLength(name);
    }

    private void validateNameNotBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new CouponApplicationException("멤버의 이름은 비어있을 수 없습니다");
        }
    }

    private void validateNameLength(String name) {
        final var nameLength = name.length();
        if (MIN_NAME_LENGTH > nameLength || nameLength > MAX_NAME_LENGTH) {
            throw new CouponApplicationException(
                    "멤버 이름의 길이는 " + MIN_NAME_LENGTH + "이상, " + MAX_NAME_LENGTH + "이하여야 합니다"
            );
        }
    }
}
