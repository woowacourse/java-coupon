package coupon.member.exception;

public class MemberException extends RuntimeException {

    private final MemberErrorMessage errorMessage;

    public MemberException(MemberErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MemberErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
