package coupon.member.service;

import coupon.member.domain.Member;
import coupon.member.dto.MemberRequest;
import coupon.member.dto.MemberResponse;
import coupon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse create(MemberRequest request) {
        Member member = memberRepository.save(request.toEntity());
        return MemberResponse.from(member);
    }
}
