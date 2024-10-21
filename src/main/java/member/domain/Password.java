package member.domain;

import lombok.Getter;

@Getter
public class Password {

    private final String password;

    public Password(String password) {
        validateEmptyPassword(password);
        this.password = password;
    }

    private void validateEmptyPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("패스워드는 null일 수 없습니다.");
        }

        if (password.isEmpty()) {
            throw new IllegalArgumentException("패스워드는 빈 값일 수 없습니다.");
        }
    }

    public boolean isCorrect(Password other) {
        return this.password.equals(other.password);
    }
}
