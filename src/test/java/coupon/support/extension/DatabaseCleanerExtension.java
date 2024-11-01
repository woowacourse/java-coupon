package coupon.support.extension;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

public class DatabaseCleanerExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        ApplicationContext context = SpringExtension.getApplicationContext(extensionContext);
        cleanup(context);
        cleanCache(context);
    }

    private void cleanup(ApplicationContext context) {
        EntityManager em = context.getBean(EntityManager.class);
        TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);

        transactionTemplate.execute(action -> {
            em.clear();
            truncateTables(em);
            return null;
        });
    }

    private void truncateTables(EntityManager em) {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        for (String tableName : findTableNames(em)) {
            em.createNativeQuery("TRUNCATE TABLE %s".formatted(tableName)).executeUpdate();
        }
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }

    @SuppressWarnings("unchecked")
    private List<String> findTableNames(EntityManager em) {
        return em.createNativeQuery("SHOW TABLES").getResultList();
    }

    private void cleanCache(ApplicationContext context) {
        RedisTemplate<?, ?> redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);
        redisTemplate.getConnectionFactory()
                .getConnection()
                .serverCommands()
                .flushAll();
    }
}
