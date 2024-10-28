package coupon.service;

import coupon.domain.Member;
import coupon.dto.MemberRequest;
import coupon.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member create(MemberRequest memberRequest) {
        return memberRepository.save(memberRequest.toEntity());
    }
}
