package coupon.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import coupon.domain.coupon.Coupon;
import coupon.repository.ReaderRepository;
import coupon.repository.WriterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final WriterRepository writerRepository;
    private final ReaderRepository readerRepository;

    public void create(Coupon coupon) {
        writerRepository.save(coupon);
    }

    public Coupon getCoupon(long id) {
        final Optional<Coupon> byId = readerRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return writerRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
