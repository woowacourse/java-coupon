package coupon;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 10;

    private Long id;

    private String name;

    public Member(Long id, String name) {
        if (name.isBlank() || 10 < name.length()) {
            throw new IllegalArgumentException(String.format("회원의 이름은 최소 %d자 이상 %d자 이하만 가능합니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
        this.id = id;
        this.name = name;
    }
}
