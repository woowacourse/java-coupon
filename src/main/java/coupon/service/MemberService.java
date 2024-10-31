package coupon.service;

import coupon.domain.Member;
import coupon.repository.MemberRepository;
import coupon.util.TransactionWriterExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TransactionWriterExecutor transactionWriterExecutor;

    public long create(Member member) {
        return memberRepository.save(member).getId();
    }

    public Member read(Long id) {
        return memberRepository.findById(id).
                orElse(findById(id));
    }

    private Member findById(Long id) {
        return transactionWriterExecutor.execute(
                () -> memberRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다. id=" + id))
        );
    }
}
