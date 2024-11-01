package coupon.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class DataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.info("Use dataSource : READER");
            return DataSourceType.READER;
        }

        DataSourceType dataSource = DataSourceContextHolder.getDataSource();
        if (dataSource != null) {
            log.info("Use dataSource : " + dataSource.name());
            return dataSource;
        }

        log.info("Use dataSource : WRITER");
        return DataSourceType.WRITER;
    }
}
