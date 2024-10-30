package coupon.service;

import coupon.domain.Member;
import coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(String name) {
        final Member member = new Member(name);
        return memberRepository.save(member);
    }
}
