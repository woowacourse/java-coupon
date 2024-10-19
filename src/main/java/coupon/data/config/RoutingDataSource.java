package coupon.data.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class RoutingDataSource extends AbstractRoutingDataSource {

    public RoutingDataSource(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(targetDataSources.get(DataSourceType.WRITER));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionName() + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly() && isReplicationFinished()) {
            return DataSourceType.READER;
        }
        return DataSourceType.WRITER;
    }

    private boolean isReplicationFinished() {
        Map<Object, DataSource> resolvedDataSources = getResolvedDataSources();
        DataSource readerSource = resolvedDataSources.get(DataSourceType.READER);
        JdbcTemplate template = new JdbcTemplate(readerSource);
        Map<String, Object> showSlaveStatus = template.queryForMap("show slave status");
        return !showSlaveStatus.get("Slave_SQL_Running_State").toString().contains("Waiting");
    }
}
