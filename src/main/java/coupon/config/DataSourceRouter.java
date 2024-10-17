package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    public static final String READ_DATASOURCE_KEY = "read";
    public static final String WRITE_DATASOURCE_KEY = "write";

    private static final ThreadLocal<Boolean> isImmediateReadMode = ThreadLocal.withInitial(() -> false);

    public static void setImmediateReadMode(boolean isimmediateReadMode) {
        isImmediateReadMode.set(isimmediateReadMode);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (isImmediateReadMode.get()) {
            return WRITE_DATASOURCE_KEY;
        }
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return READ_DATASOURCE_KEY;
        }
        return WRITE_DATASOURCE_KEY;
    }
}
