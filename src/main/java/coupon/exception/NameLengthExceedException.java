package coupon.exception;

public class NameLengthExceedException extends RuntimeException {
    public NameLengthExceedException(int MAX_LENGTH) {
        super(String.format("이름의 길이는 %d를 넘을 수 없습니다.", MAX_LENGTH));
    }
}
