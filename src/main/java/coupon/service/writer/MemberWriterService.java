package coupon.service.writer;

import coupon.domain.Member;
import coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberWriterService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member create(Member member) {
        return memberRepository.save(member);
    }
}
