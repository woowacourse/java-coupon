package coupon.support.cleaner;


import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    @Transactional
    public void clear() {
        em.clear();
        truncate();
        clearCache();
    }

    private void truncate() {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        getTruncateQueries().forEach(query -> em.createNativeQuery(query).executeUpdate());
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }

    private List<String> getTruncateQueries() {
        String showTablesSql = "SHOW TABLES";
        List<String> tableNames = em.createNativeQuery(showTablesSql).getResultList();

        return tableNames.stream()
                .map(tableName -> "TRUNCATE TABLE " + tableName)
                .toList();
    }

    private void clearCache() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }
}
