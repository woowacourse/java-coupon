package coupon.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import org.springframework.data.redis.core.RedisHash;

@Entity
@RedisHash(value = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected Member(){
    }

    public Long getId() {
        return id;
    }
}
