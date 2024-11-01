package config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadOnlyDataSourceRouter extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		if(TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
			log.info("읽기 db 열일중");
			return DataSourceType.READER;
		}
		log.info("쓰기 db 열일중");
		return DataSourceType.WRITER;
	}
}
