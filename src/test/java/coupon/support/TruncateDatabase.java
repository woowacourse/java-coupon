package coupon.support;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.support.TransactionTemplate;

public class TruncateDatabase implements AfterEachCallback {

    private static final String SHOW_TABLES = "SHOW TABLES";
    private static final String TRUNCATE_FORMAT = "TRUNCATE TABLE %s";
    private static final String RESTART_ID_FORMAT = "ALTER TABLE %s ALTER COLUMN id RESTART WITH 1";

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        ApplicationContext context = SpringExtension.getApplicationContext(extensionContext);
        cleanup(context);
    }

    private void cleanup(ApplicationContext context) {
        EntityManager em = context.getBean(EntityManager.class);
        TransactionTemplate transactionTemplate = context.getBean(TransactionTemplate.class);
        transactionTemplate.execute(action -> {
            em.clear();
            List<String> tableNames = getTableNames(em);
            tableNames.forEach(
                    tableName -> em.createNativeQuery(String.format(TRUNCATE_FORMAT, tableName)).executeUpdate());
            tableNames.forEach(
                    tableName -> em.createNativeQuery(String.format(RESTART_ID_FORMAT, tableName)).executeUpdate());
            return null;
        });
    }

    private List<String> getTableNames(EntityManager em) {
        List<Object[]> results = em.createNativeQuery(SHOW_TABLES).getResultList();
        return results.stream()
                .map(row -> (String) row[0])
                .toList();
    }
}
