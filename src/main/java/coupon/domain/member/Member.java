package coupon.domain.member;

import coupon.infra.db.MemberEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Member {

    private final Long id;

    private final String email;

    private final String password;

    public Member(String email, String password) {
        this(null, email, password);
    }

    public static Member from(MemberEntity entity) {
        return new Member(entity.getId(), entity.getEmail(), entity.getPassword());
    }

    public boolean isIdOf(Long id) {
        return this.id.equals(id);
    }
}
