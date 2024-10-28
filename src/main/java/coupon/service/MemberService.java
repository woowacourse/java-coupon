package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Member;
import coupon.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void create(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAllInBatch();
    }
}
