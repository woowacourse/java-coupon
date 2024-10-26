package coupon.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.member.domain.Member;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("캐시를 사용해 생성한 멤버를 복제 지연 문제 없이 불러올 수 있다.")
    @Test
    void testCreateAndReadMemberWithCache() {
        // given
        Member member = new Member("멤버");

        // when
        memberService.createWithCache(member);
        Optional<Member> savedMember = memberService.readByIdFromReaderWithCache(member.getId());

        // then
        assertThat(savedMember).isPresent();
    }
}
