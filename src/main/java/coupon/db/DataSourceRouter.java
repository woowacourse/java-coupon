package coupon.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceRouter.class);

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        boolean isTransactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        DataSourceType dataSourceType = DataSourceType.mapTo(isReadOnly, isTransactionActive);
        logger.info("readOnly : {}, isTransactionActive : {}, dataSourceType : {}", isReadOnly, isTransactionActive, dataSourceType);
        return dataSourceType;
    }
}
