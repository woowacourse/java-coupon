package coupon.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    public static final String READER_SERVER = "reader";
    public static final String WRITER_SERVER = "writer";

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.debug("Reader dataSource used");
            return READER_SERVER;
        }

        log.debug("Writer dataSource used");
        return WRITER_SERVER;
    }
}
