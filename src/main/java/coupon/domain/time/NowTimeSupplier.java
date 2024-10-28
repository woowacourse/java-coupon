package coupon.domain.time;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Primary
public class NowTimeSupplier implements TimeSupplier {

    @Override
    public LocalDateTime supply() {
        return LocalDateTime.now();
    }
}
