package coupon.dto;

import coupon.domain.Member;

public record MemberResponse(Long id) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId());
    }
}
