package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Member(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null) {
            throw new CouponException("이름은 null일 수 없습니다.");
        }

        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new CouponException(String.format("이름은 %d자 이상 %d자 이하만 입력 가능합니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }
}
