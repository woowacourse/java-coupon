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
//        clearCache();
    }

    private void truncate() {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        getTruncateQueries().forEach(query -> em.createNativeQuery(query).executeUpdate());
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }

    private List<String> getTruncateQueries() {
        String sql = """
                SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ' RESTART IDENTITY', ';') AS q
                FROM INFORMATION_SCHEMA.TABLES
                WHERE TABLE_SCHEMA = 'PUBLIC'
                """;

        return em.createNativeQuery(sql).getResultList();
    }

    private void clearCache() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }
}
