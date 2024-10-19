package coupon.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReadOnlyDataSourceRouter extends AbstractRoutingDataSource {

    Logger log = LoggerFactory.getLogger(ReadOnlyDataSourceRouter.class);

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.info("선택한 DB Type: READER");
            return DataSourceType.READER;
        }
        log.info("선택한 DB Type: WRITER");
        return DataSourceType.WRITER;
    }
}
