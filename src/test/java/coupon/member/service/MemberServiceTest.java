package coupon.member.service;

import static org.assertj.core.api.Assertions.assertThatCode;

import coupon.member.request.MemberCreateRequest;
import java.util.UUID;
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
        String randomId = UUID.randomUUID().toString();
        MemberCreateRequest request = new MemberCreateRequest(randomId, "패스워드");

        // when & then
        assertThatCode(() -> memberService.create(request))
                .doesNotThrowAnyException();
    }
}
