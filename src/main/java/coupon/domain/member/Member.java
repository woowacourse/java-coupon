package coupon.domain.member;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final MemberName memberName;

    public Member(Long id, MemberName memberName) {
        this.id = id;
        this.memberName = memberName;
    }
}
