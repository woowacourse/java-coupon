package coupon.member.entity;

import coupon.member.domain.Member;
import jakarta.persistence.*;

@Entity
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Member member;
}
