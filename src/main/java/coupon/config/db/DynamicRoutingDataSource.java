package coupon.config.db;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

import static coupon.config.db.ReaderDataSourceConfig.READER;
import static coupon.config.db.WriterDataSourceConfig.WRITER;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        if (isCurrentTransactionReadOnly()) {
            return READER;
        }
        return WRITER;
    }
}
