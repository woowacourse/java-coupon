package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReadOnlyDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly() &&
            RoutingContext.isRoutableToReadSource()) {
            return DataSourceType.READER;
        }
        return DataSourceType.WRITER;
    }
}
