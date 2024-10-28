package coupon.member.service;

import coupon.member.repository.MemberEntity;
import coupon.member.request.MemberCreateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MemberService {

    private final MemberCreator memberCreator;
    private final MemberReader memberReader;

    @Transactional
    public MemberEntity create(MemberCreateRequest request) {
        // readerdb를 사용하면 따닥 시 중복 회원 생성
        if (memberReader.isNotExist(request.name())) {
            return memberCreator.createMember(request);
        }
        throw new RuntimeException("이미 존재하는 아이디 입니다. [%s]".formatted(request.name()));
    }
}
