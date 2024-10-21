package coupon.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
public class DataSourceRouter extends AbstractRoutingDataSource {

    protected Object determineCurrentLookupKey() {
        DataSourceType dataSourceType = determineFromThreadLocal();
        if (dataSourceType != null) {
            return dataSourceType;
        }

        return determineFromTransactionSynchronization();
    }

    private DataSourceType determineFromThreadLocal() {
        DataSourceType dataSourceType = DataSourceContextHolder.getDataSourceType();
        if (dataSourceType != null) {
            log.info("ThreadLocal DB 설정: " + dataSourceType);
        }
        return dataSourceType;
    }

    private DataSourceType determineFromTransactionSynchronization() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            log.info("Read 트랜잭션 - Reader DB 사용");
            return DataSourceType.READER;
        }

        log.info("Write 트랜잭션 - Writer DB 사용");
        return DataSourceType.WRITER;
    }
}

