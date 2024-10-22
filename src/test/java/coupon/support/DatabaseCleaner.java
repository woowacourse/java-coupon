package coupon.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

    private static final String FOREIGN_KEY_CHECKS_SQL = "set foreign_key_checks = %d";
    private static final String TRUNCATE_TABLE_SQL = "truncate table %s";

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void execute() {
        entityManager.createNativeQuery(String.format(FOREIGN_KEY_CHECKS_SQL, 0)).executeUpdate();
        getTableNames().forEach(this::truncateTable);
        entityManager.createNativeQuery(String.format(FOREIGN_KEY_CHECKS_SQL, 1)).executeUpdate();
    }

    private List<String> getTableNames() {
        String sql = """
                select table_name
                from information_schema.tables
                where table_schema = 'coupon'
                """;
        return entityManager.createNativeQuery(sql).getResultList();
    }

    private void truncateTable(String tableName) {
        entityManager.createNativeQuery(String.format(TRUNCATE_TABLE_SQL, tableName)).executeUpdate();
    }
}
