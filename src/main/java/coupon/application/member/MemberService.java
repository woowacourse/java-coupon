package coupon.application.member;

import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CouponException(ExceptionType.MEMBER_NOT_FOUND));
    }
}
