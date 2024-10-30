package coupon.util;

import coupon.repository.CachedCouponRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

    private final EntityManager entityManager;
    private final CachedCouponRepository cachedCouponRepository;

    public DatabaseCleaner(EntityManager entityManager, CachedCouponRepository couponRepository) {
        this.entityManager = entityManager;
        this.cachedCouponRepository = couponRepository;
    }

    @Transactional
    public void clear() {
        entityManager.clear();
        truncate();
        cachedCouponRepository.deleteAll();
    }

    private void truncate() {
        List<String> tableNames = getTableNames();
        entityManager.createNativeQuery("SET foreign_key_checks = 0").executeUpdate();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET foreign_key_checks = 1").executeUpdate();
    }

    private List<String> getTableNames() {
        return entityManager.createNativeQuery("SHOW TABLES")
                .getResultList()
                .stream()
                .map(String::valueOf)
                .toList();
    }
}
