package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    protected static String READER_DB_KEY = "reader";
    protected static String WRITER_DB_KEY = "writer";

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return READER_DB_KEY;
        }
        return WRITER_DB_KEY;
    }
}
