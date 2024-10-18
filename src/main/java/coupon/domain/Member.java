package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Member {

    @Id
    private Long id;

    protected Member(){
    }
}
