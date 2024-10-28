package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        boolean currentTransactionReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (currentTransactionReadOnly) {
            return DataSourceType.READER;
        }
        return DataSourceType.WRITER;
    }
}
