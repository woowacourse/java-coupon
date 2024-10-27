package coupon.service;

import coupon.domain.Member;
import coupon.service.writer.MemberWriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberWriterService writerService;

    @Transactional
    public Member createMember(String name) {
        final Member member = new Member(name);
        return writerService.create(member);
    }
}
