package coupon;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import coupon.util.DatabaseCleanerExtension;

@ExtendWith(DatabaseCleanerExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class ServiceSliceTest {
}
