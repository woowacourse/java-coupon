package coupon.support;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import coupon.domain.Member;
import coupon.domain.MemberRepository;

public class MemberMockRepository implements MemberRepository {

    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final Map<Long, Member> members = new HashMap<>();

    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            long newId = idGenerator.getAndIncrement();
            Member newMember = new Member(newId, member.getName());
            members.put(newId, newMember);
            return newMember;
        }
        members.put(member.getId(), member);
        return member;
    }
}
