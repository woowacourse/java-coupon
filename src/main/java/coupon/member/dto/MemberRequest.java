package coupon.member.dto;

import coupon.member.domain.Member;

public record MemberRequest(String name) {

    public Member toEntity() {
        return new Member(name);
    }
}
