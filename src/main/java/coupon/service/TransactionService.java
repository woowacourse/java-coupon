package coupon.service;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T run(Supplier<T> supplier) {
        return supplier.get();
    }
}
