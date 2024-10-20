package coupon.domain.member.repository;

import coupon.domain.member.Member;
import coupon.infra.db.jpa.JpaMemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdaptor implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Optional<Member> findById(Long memberId) {
        return jpaMemberRepository.findById(memberId).map(Member::from);
    }
}
