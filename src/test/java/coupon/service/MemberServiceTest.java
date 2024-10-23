package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("멤버를 생성하여 아이디를 부여한다.")
    void create() {
        // given
        String name = "Seyang";

        // when
        Member member = memberService.create(name);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isEqualTo(name);
    }
}
