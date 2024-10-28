package coupon.service;

import coupon.domain.User;
import coupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getUsersByCouponCount(int couponCount) {
        return userRepository.findAllByCouponCount(couponCount);
    }
}
