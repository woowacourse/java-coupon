package coupon.service;

import coupon.helper.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class ServiceTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @BeforeEach
    protected void setUp() {
        databaseCleaner.execute();
    }
}
