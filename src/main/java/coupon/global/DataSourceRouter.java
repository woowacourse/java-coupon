package coupon.global;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    private static final ThreadLocal<DataSourceType> currentDataSource = new ThreadLocal<>();

    public static void setCurrentDataSource(DataSourceType dataSourceType) {
        currentDataSource.set(dataSourceType);
    }

    public static void clearDataSource() {
        currentDataSource.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType dataSourceType = currentDataSource.get();

        if (dataSourceType != null) {
            return dataSourceType;
        }

        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.READER;
        }
        return DataSourceType.WRITER;
    }
}
