package coupon.quizhelper.dto;

import java.util.List;

public record InitializeUsedRequest(List<Long> memberCouponIds) {
}
