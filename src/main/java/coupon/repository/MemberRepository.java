package coupon.repository;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<MemberEntity, Long> {

    MemberEntity save(MemberEntity member);

    Optional<MemberEntity> findById(Long id);
}
