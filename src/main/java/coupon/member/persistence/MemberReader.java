package coupon.member.persistence;

import coupon.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberReader {

    private final MemberRepository memberRepository;

    public Member getMember(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("%d와 일치하는 회원이 존재하지 않습니다.", memberId))
                );
    }
}
