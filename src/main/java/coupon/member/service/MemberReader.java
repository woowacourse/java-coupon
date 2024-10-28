package coupon.member.service;

import coupon.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberReader {

    private final MemberRepository memberRepository;

    public boolean isNotExist(String name) {
        return !memberRepository.existsByName(name);
    }
}
