package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    private static final ThreadLocal<Boolean> isWriterForced = new ThreadLocal<>();

    static {
        isWriterForced.set(false);
    }

    public static void forceUseWriterDatabase() {
        isWriterForced.set(true);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (isWriterForced.get()) {
            return DataSourceType.WRITER;
        }

        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.READER;
        }

        return DataSourceType.WRITER;
    }
}
