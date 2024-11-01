package coupon.dto;

import coupon.domain.Member;

public record MemberRequest(String name) {
    public Member toEntity() {
        return new Member(name);
    }
}
