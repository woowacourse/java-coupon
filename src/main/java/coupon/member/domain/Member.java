package coupon.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Nickname nickname;

    protected Member() {
    }

    public Member(String nickname) {
        this(new Nickname(nickname));
    }

    private Member(Nickname nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }
}
