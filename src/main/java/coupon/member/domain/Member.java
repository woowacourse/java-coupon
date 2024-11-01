package coupon.member.domain;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final MemberName name;

    public Member(final Long id, final String name) {
        this(id, new MemberName(name));
    }

    public Member(final Long id, final MemberName name) {
        validateName(name);
        this.id = id;
        this.name = name;
    }

    private void validateName(final MemberName name) {
        if (name == null) {
            throw new IllegalArgumentException("회원 이름으로 null이 입력될 수 없습니다.");
        }
    }
}
