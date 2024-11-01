package coupon.entity;

import coupon.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public MemberEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MemberEntity from(Member member) {
        return new MemberEntity(member.getId(), member.getName());
    }

    public Member toMember() {
        return new Member(id, name);
    }
}
