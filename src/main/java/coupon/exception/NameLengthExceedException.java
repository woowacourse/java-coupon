package coupon.exception;

public class NameLengthExceedException extends Exception {
    public NameLengthExceedException(String MAX_LENGTH) {
        super(String.format("이름의 길이는 %s를 넘을 수 없습니다.", MAX_LENGTH));
    }
}
