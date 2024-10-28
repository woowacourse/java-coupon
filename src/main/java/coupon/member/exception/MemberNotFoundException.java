package coupon.member.exception;

import coupon.advice.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException(long memberId) {
        super(String.format("%d에 해당하는 사용자를 찾을 수 없습니다.", memberId));
    }
}
