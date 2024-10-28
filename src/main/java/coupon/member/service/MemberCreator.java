package coupon.member.service;

import coupon.member.domain.HashedPasswordCreator;
import coupon.member.domain.Name;
import coupon.member.domain.Password;
import coupon.member.domain.SaltGenerator;
import coupon.member.repository.MemberEntity;
import coupon.member.repository.MemberRepository;
import coupon.member.request.MemberCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberCreator {

    private final MemberRepository memberRepository;
    private final HashedPasswordCreator hashedPasswordCreator;
    private final SaltGenerator saltGenerator;

    public MemberEntity createMember(MemberCreateRequest request) {
        Name name = new Name(request.name());
        String plainPassword = request.password();
        String salt = saltGenerator.generate();
        Password password = hashedPasswordCreator.createPassword(plainPassword, salt);
        MemberEntity memberEntity = new MemberEntity(name, password, salt);
        return memberRepository.save(memberEntity);
    }
}
