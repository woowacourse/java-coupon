package coupon.domain.member.repository;

import coupon.domain.member.Member;
import coupon.infra.db.MemberEntity;
import coupon.infra.db.jpa.JpaMemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdaptor implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    @Override
    public Member save(Member member) {
        MemberEntity saved = jpaMemberRepository.save(toEntity(member));
        return Member.from(saved);
    }

    private MemberEntity toEntity(Member member) {
        return new MemberEntity(member.getId(), member.getEmail(), member.getPassword());
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return jpaMemberRepository.findById(memberId).map(Member::from);
    }
}
