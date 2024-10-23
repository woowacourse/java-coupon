package coupon.datasource.config;

import coupon.datasource.DataSourceRoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        if (DataSourceRoutingContext.isWrite()) {
            log.info("Routing at writer");
            return "writer";
        }

        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.info("Routing at reader");
            return "reader";
        }

        log.info("Routing at writer");
        return "writer";
    }
}
