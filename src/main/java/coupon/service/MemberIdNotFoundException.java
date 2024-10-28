package coupon.service;

import coupon.exception.CustomException;

public class MemberIdNotFoundException extends CustomException {
    public MemberIdNotFoundException(String message) {
        super(message);
    }
}
