package member.domain;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final String account;
    private final String password;

    public Member(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public Member(String account, String password) {
        this(null, account, password);
    }
}
