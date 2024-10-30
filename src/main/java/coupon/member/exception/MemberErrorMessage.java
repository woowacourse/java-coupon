package coupon.member.exception;

public enum MemberErrorMessage {

    CANNOT_FIND_MEMBER("존재하지 않는 사용자입니다."),
    ;

    private final String message;

    MemberErrorMessage(String message) {
        this.message = message;
    }
}
