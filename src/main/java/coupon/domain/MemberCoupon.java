package coupon.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCoupon {

    private final long memberId;
    private final long couponId;
    private final boolean isUsed;
    private final LocalDateTime issuedDate;
    private final LocalDateTime expirationDate;
}

