package coupon.member.service;

import java.util.Optional;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final String CACHE_NAMES = "members";

    private final MemberRepository memberRepository;

    @Transactional
    @CachePut(value = CACHE_NAMES, key = "#result.id")
    public Member createWithCache(Member member) {
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAMES, key = "#memberId")
    public Optional<Member> readByIdFromReaderWithCache(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
