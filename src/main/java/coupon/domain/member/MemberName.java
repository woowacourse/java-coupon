package coupon.domain.member;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberName {

    @Column(name = "name", nullable = false)
    private String value;

    public MemberName(String name) {
        validate(name);
        this.value = name;
    }

    private void validate(String name) {
        if (Strings.isBlank(name)) {
            throw new CouponException("회원 이름은 필수입니다.");
        }
    }
}
