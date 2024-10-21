package coupon.exception;

public class NameNotExistException extends Exception{
    public NameNotExistException() {
        super("이름을 비어있을 수 없습니다.");
    }
}
