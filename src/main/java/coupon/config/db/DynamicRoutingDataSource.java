package coupon.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        if (isCurrentTransactionReadOnly()) {
            return DataSourceConstants.READER;
        }
        return DataSourceConstants.WRITER;
    }
}
