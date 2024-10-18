package coupon.repository;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<MemberEntity, Long> {

    MemberEntity save(MemberEntity member);

    Optional<MemberEntity> findById(long id);

    default MemberEntity findByIdOrThrow(long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }
}
