package coupon.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import coupon.member.domain.Member;
import coupon.member.service.port.MemberRepository;

public class FakeMemberRepository implements MemberRepository {

    public final List<Member> storage = new ArrayList<>();

    @Override
    public Optional<Member> findById(final Long id) {
        return storage.stream()
                .filter(member -> member.getId().equals(id))
                .findAny();
    }
}
