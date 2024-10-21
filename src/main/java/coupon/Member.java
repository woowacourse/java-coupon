package coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Member(String name) {
        if (name.isBlank() || 10 < name.length()) {
            throw new IllegalArgumentException(String.format("회원의 이름은 최소 %d자 이상 %d자 이하만 가능합니다.", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
        this.name = name;
    }
}
