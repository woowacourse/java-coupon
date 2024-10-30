package coupon.application.memer;

import coupon.domain.member.Member;

public record MemberResponse(Long id, String email) {

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getEmail());
    }
}
