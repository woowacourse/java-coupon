package coupon.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        boolean isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        return DataSourceType.mapTo(isReadOnly, isTransactionActive);
    }
}
