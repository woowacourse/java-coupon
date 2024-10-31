package coupon.controller;

import coupon.domain.Member;
import coupon.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    ResponseEntity<Long> createMember() {
        Member member = memberService.createMember();

        return ResponseEntity.ok(member.getId());
    }
}
