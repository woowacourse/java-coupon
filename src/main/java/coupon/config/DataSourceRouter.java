package coupon.config;

import static coupon.config.DataSourceConfig.READ_DATASOURCE;
import static coupon.config.DataSourceConfig.WRITE_DATASOURCE;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return READ_DATASOURCE;
        }
        return WRITE_DATASOURCE;
    }
}
