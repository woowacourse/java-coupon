package coupon.support;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

public class DatabaseClearExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        ApplicationContext ac = SpringExtension.getApplicationContext(context);
        clearUp(ac);
    }

    private void clearUp(ApplicationContext ac) {
        EntityManager entityManager = ac.getBean(EntityManager.class);
        TransactionTemplate transactionTemplate = ac.getBean(TransactionTemplate.class);
        transactionTemplate.execute((action) -> {
            entityManager.clear();
            truncate(entityManager);
            return null;
        });
    }

    private void truncate(EntityManager entityManager) {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        findAllTables(entityManager)
                .forEach(table -> entityManager.createNativeQuery("TRUNCATE TABLE %s".formatted(table)).executeUpdate());
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }

    @SuppressWarnings("unchecked")
    private List<String> findAllTables(EntityManager entityManager) {
        return entityManager.createNativeQuery("SHOW TABLES").getResultList();
    }
}
