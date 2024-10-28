package coupon.util;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void cleanUp() {
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        for (String tableName : getTableNames()) {
            entityManager.createNativeQuery("TRUNCATE TABLE %s".formatted(tableName)).executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
    }

    private List<String> getTableNames() {
        return entityManager.getMetamodel().getEntities().stream()
                .filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
                .map(entityType -> camelToSnake(entityType.getName()))
                .toList();
    }

    private String camelToSnake(String camel) {
        StringBuilder snake = new StringBuilder();
        for (char c : camel.toCharArray()) {
            if (Character.isUpperCase(c)) {
                snake.append("_");
            }
            snake.append(Character.toLowerCase(c));
        }
        if (snake.charAt(0) == '_') {
            snake.deleteCharAt(0);
        }
        return snake.toString();
    }
}
