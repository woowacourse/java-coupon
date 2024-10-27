package coupon.config;

import static coupon.config.DataSourceConfig.READ_DATASOURCE;
import static coupon.config.DataSourceConfig.WRITE_DATASOURCE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class DataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            logger.debug("Current DataSource key: readDataSource");
            return READ_DATASOURCE;
        }
        logger.debug("Current DataSource key: writeDataSource");
        return WRITE_DATASOURCE;
    }
}
