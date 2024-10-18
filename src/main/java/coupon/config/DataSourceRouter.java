package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    private static final ThreadLocal<Boolean> defaultDataSourceType = ThreadLocal.withInitial(() -> false);

    public static void setDataSourceTypeAsRead() {
        defaultDataSourceType.set(true);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (Boolean.TRUE.equals(defaultDataSourceType.get())) {
            return DataSourceType.WRITE;
        }
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.READ;
        }
        return DataSourceType.WRITE;
    }
}
