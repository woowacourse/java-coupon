package coupon.member.persistence;

import coupon.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class MemberWriter {

    private final MemberRepository memberRepository;

    public Member create(Member member) {
        return memberRepository.save(member);
    }
}
