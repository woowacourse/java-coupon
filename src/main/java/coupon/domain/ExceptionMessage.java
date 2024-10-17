package coupon.domain;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    NAME_LENGTH_EXCEPTION("이름은 반드시 존재해야 하며, 최대 %d자 이하여야 합니다"),
    DISCOUNT_AMOUNT_EXCEPTION("할인 금액은 %d원 이상, %d원 이하이며 %d원 단위로 설정해야 합니다"),
    MINIMUM_ORDER_AMOUNT_EXCEPTION("최소 주문 금액은 %d원 이상, %d원 이하여야 합니다"),
    DISCOUNT_RATE_EXCEPTION("할인율은 %d%% 이상, %d%% 이하여야 합니다"),
    START_DATE_BEFORE_END_DATE_EXCEPTION("시작일은 종료일보다 이전이어야 합니다"),
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
