package coupon.infrastructure;

import org.springframework.stereotype.Repository;

import coupon.domain.Member;
import coupon.domain.MemberRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberCoreRepository implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }
}
