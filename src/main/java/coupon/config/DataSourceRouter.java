package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.READER;
        }

        DataSourceType dataSource = DataSourceContextHolder.getDataSource();
        if (dataSource != null) {
            return dataSource;
        }

        return DataSourceType.WRITER;
    }
}
