package coupon.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class MemberCoupon implements Serializable {

    private final long memberId;
    private final long couponId;
    private final boolean isUsed;
    private final LocalDateTime issuedDate;
    private final LocalDateTime expirationDate;
}

