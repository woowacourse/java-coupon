package coupon.support;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class TestClearExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        TestCleaner databaseCleaner = getDataCleaner(context);
        RedisConnection redisConnection = getRedisConnection(context);
        redisConnection.flushAll();
        databaseCleaner.clear();
    }

    private TestCleaner getDataCleaner(ExtensionContext extensionContext) {
        return SpringExtension.getApplicationContext(extensionContext)
                .getBean(TestCleaner.class);
    }

    private RedisConnection getRedisConnection(ExtensionContext extensionContext) {
        RedisConnectionFactory connectionFactory = SpringExtension.getApplicationContext(extensionContext)
                .getBean(RedisConnectionFactory.class);

        return connectionFactory.getConnection();
    }
}
