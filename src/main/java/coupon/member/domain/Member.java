package coupon.member.domain;

public class Member {

    private final Long id;
    private final MemberName name;

    public Member(final Long id, final String name) {
        this(id, new MemberName(name));
    }

    public Member(final Long id, final MemberName name) {
        validateId(id);
        validateName(name);
        this.id = id;
        this.name = name;
    }

    private void validateId(final Long id) {
        validateIdIsNegative(id);
    }

    private void validateIdIsNegative(final Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("회원 쿠폰 id로 음수가 입력될 수 없습니다.");
        }
    }

    private void validateName(final MemberName name) {
        if (name == null) {
            throw new IllegalArgumentException("회원 이름으로 null이 입력될 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
