package coupon.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Bean
	@ConfigurationProperties(prefix = "coupon.datasource.writer")
	public DataSource writerDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "coupon.datasource.reader")
	public DataSource readerDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public DataSource routingDataSource(DataSource writerDataSource, DataSource readerDataSource) {
		ReadOnlyDataSourceRouter routingDataSource = new ReadOnlyDataSourceRouter();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.WRITER, writerDataSource);
		targetDataSources.put(DataSourceType.READER, readerDataSource);
		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.setDefaultTargetDataSource(writerDataSource);
		return routingDataSource;
	}

	@Primary
	@Bean
	public DataSource dataSource() {
		return new LazyConnectionDataSourceProxy(routingDataSource(writerDataSource(), readerDataSource()));
	}
}
