package coupon;

import java.util.Map;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    public RoutingDataSource(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(targetDataSources.get(DataSourceType.WRITER));
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.READER;
        }
        return DataSourceType.WRITER;
    }
}
