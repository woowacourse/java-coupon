package coupon.service;

import coupon.support.TruncateDatabase;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ExtendWith(TruncateDatabase.class)
public abstract class ServiceTest {

}
