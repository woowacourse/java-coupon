package coupon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    public static final String READER_SERVER = "reader";
    public static final String WRITER_SERVER = "writer";
    private static final Logger logger = LoggerFactory.getLogger(RoutingDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            logger.debug("Using Reader DataSource");
            return READER_SERVER;
        }

        logger.debug("Using Writer DataSource");
        return WRITER_SERVER;
    }
}
