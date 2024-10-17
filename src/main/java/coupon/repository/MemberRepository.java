package coupon.repository;

import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<MemberEntity, Long> {

    MemberEntity save(MemberEntity member);
}
