package coupon.infrastructure;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    private static final ThreadLocal<DataSourceType> CONTEXT_HOLDER
            = ThreadLocal.withInitial(() -> DataSourceType.READ);

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.READ;
        }
        return DataSourceType.WRITE;
    }

    public static void setDataSourceKey(DataSourceType dataSourceKey) {
        CONTEXT_HOLDER.set(dataSourceKey);
    }
}
