package coupon.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "member")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String account;

    @Column
    private String password;

    public Member() {
    }

    public Member(Long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    public Member(String account, String password) {
        this(null, account, password);
    }
}
