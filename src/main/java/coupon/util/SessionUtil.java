package coupon.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SessionUtil {
    @PersistenceContext
    private final EntityManager entityManager;
    private final DataSource dataSource;

    public SessionUtil(final EntityManager entityManager, final DataSource dataSource) {
        this.entityManager = entityManager;
        this.dataSource = dataSource;
    }

    public void logSessionStatus() {
        logSessionStatus("");
    }

    public void logSessionStatus(final String title) {

        final Session session = entityManager.unwrap(Session.class);
        final Connection connection = session
                .doReturningWork(connection1 -> connection1.unwrap(Connection.class));

        try {
            System.out.printf("=================================%s================================%n", title);
            System.out.println("=================================================================");
            System.out.println(connection);
            System.out.println("Connection URL: " + connection.getMetaData()
                    .getURL());
            System.out.println("Connection User: " + connection.getMetaData()
                    .getUserName());
            logEntityKeys(session);
            System.out.println("=================================================================");
            System.out.println("=================================================================");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void logEntityKeys(final Session session) {
        final var keys = session.getStatistics()
                .getEntityKeys();
        if (keys.isEmpty()) {
            System.out.println("No entity keys found");
            return;
        }
        session.getStatistics()
                .getEntityKeys()
                .forEach(entityName -> {
                    System.out.println("Managed entity: " + entityName);
                });
    }

    public void close() {
        final Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            connection.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
