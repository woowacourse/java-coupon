package coupon.exception;

public class NameNotExistException extends RuntimeException {
    public NameNotExistException() {
        System.out.println("이름을 비어있을 수 없습니다.");
    }

}
