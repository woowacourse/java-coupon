package coupon.service;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReaderService {

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public <T> T read(Supplier<T> execution) {
        return execution.get();
    }
}
