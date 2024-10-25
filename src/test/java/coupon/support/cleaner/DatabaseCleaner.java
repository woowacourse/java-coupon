package coupon.support.cleaner;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void clear() {
        em.clear();
        truncate();
    }

    private void truncate() {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        getTruncateQueries().forEach(query -> em.createNativeQuery(query).executeUpdate());
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    private List<String> getTruncateQueries() {
        String sql = """
                SELECT CONCAT('TRUNCATE TABLE ', TABLE_NAME, ';') AS q
                FROM INFORMATION_SCHEMA.TABLES
                WHERE TABLE_SCHEMA = 'coupons'
                """;

        return em.createNativeQuery(sql).getResultList();
    }
}
