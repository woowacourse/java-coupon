package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    private static final ThreadLocal<DataSourceType> currentDataSource = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        if (currentDataSource.get() != null) {
            return currentDataSource.get();
        }
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.READER;
        }
        return DataSourceType.WRITER;
    }

    public static void setDataSourceType(DataSourceType dataSourceType) {
        currentDataSource.set(dataSourceType);
    }

    public static void clearDataSourceType() {
        currentDataSource.remove();
    }
}
