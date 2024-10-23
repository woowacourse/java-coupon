package coupon.service;

import coupon.domain.Member;
import coupon.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member create(String name) {
        return memberRepository.save(new Member(name));
    }
}
