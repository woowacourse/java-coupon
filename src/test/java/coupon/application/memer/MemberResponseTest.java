package coupon.application.memer;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberResponseTest {

    @Test
    @DisplayName("도메인의 값을 통해 객체를 생성한다.")
    void from() {
        Member member = new Member(1L, "test@test.com", "password");

        MemberResponse from = MemberResponse.from(member);

        assertThat(from)
                .isEqualTo(new MemberResponse(1L, "test@test.com"));
    }
}
