package coupon.member.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import coupon.member.domain.Member;
import coupon.member.service.port.MemberRepository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public MemberRepositoryImpl(final MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Optional<Member> findById(final Long id) {
        return memberJpaRepository.findById(id)
                .map(MemberEntity::toDomain);
    }
}
