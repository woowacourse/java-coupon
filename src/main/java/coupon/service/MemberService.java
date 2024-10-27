package coupon.service;

import coupon.domain.Member;
import coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember() {
        return memberRepository.save(new Member());
    }

    @Transactional(readOnly = true)
    public Member readMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 존재하지 않습니다. id : " + id));
    }
}
