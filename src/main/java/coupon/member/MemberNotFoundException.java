package coupon.member;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(long memberId) {
        super("일치하는 회원 정보가 없습니다. memberId : " + memberId);
    }
}
