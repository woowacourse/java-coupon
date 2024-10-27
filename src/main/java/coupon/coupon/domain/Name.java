package coupon.coupon.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Name {

    @Column(length = 30, nullable = false)
    private String name;

    public Name(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if(name.isBlank()){
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }
}
