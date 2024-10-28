package coupon.entity.member;

import coupon.exception.member.MemberNameException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class MemberName {

    private static final int MAX_NAME_LENGTH = 15;
    private String name;

    public MemberName(String name) {
        this.name = name;
        validate();
    }

    private void validate() {
        if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new MemberNameException(name);
        }
    }
}
