package coupon.member.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.member.request.MemberCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("맴버 등록에 성공한다.")
    @Test
    void createMember() {
        // given
        MemberCreateRequest request = new MemberCreateRequest("테바", "패스워드");

        // when & then
        assertThatCode(() -> memberService.create(request))
                .doesNotThrowAnyException();
    }
}
