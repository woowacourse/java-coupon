package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    public static final String READ_DATA_SOURCE_KEY = "read";
    public static final String WRITE_DATA_SOURCE_KEY = "write";
    public static final long REPLICATION_DELAY_SECONDS = 60;

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly() &&
                !CouponRoutingContext.isRecentlyCreated(REPLICATION_DELAY_SECONDS)) {
            return READ_DATA_SOURCE_KEY;
        }
        return WRITE_DATA_SOURCE_KEY;
    }
}
