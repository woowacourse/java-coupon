package coupon.exception;

public class EmptyNameException extends CustomException {
    public EmptyNameException() {
        super("이름을 비어있을 수 없습니다.");
    }
}
