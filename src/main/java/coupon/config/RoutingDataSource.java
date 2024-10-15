package coupon.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return isReadOnly() ? "reader" : "writer";
    }

    private boolean isReadOnly() {
        System.out.println("Read Only");
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly();
    }
}
