package coupon.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

public class TestCleaner {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void clear() {
        em.clear();
        truncate();
    }

    private void truncate() {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        truncateTable();
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    private void truncateTable() {
        em.createNativeQuery("TRUNCATE TABLE Coupon").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE MemberCoupon").executeUpdate();
    }
}
