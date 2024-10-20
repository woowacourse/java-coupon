package coupon.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Member {

    private final Long id;

    public boolean isIdOf(Long id) {
        return this.id.equals(id);
    }
}
