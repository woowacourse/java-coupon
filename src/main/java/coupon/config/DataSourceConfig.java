package coupon.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    private static final String WRITER_DATA_SOURCE = "writerDataSource";
    private static final String READER_DATA_SOURCE = "readerDataSource";
    private static final String DATA_SOURCE_ROUTER = "dataSourceRouter";

    @Bean(name = WRITER_DATA_SOURCE)
    @ConfigurationProperties("coupon.datasource.writer")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = READER_DATA_SOURCE)
    @ConfigurationProperties("coupon.datasource.reader")
    public DataSource readerDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = DATA_SOURCE_ROUTER)
    public DataSource dataSource(
            @Qualifier(WRITER_DATA_SOURCE) DataSource writerDataSource,
            @Qualifier(READER_DATA_SOURCE) DataSource readerDataSource
    ) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.WRITER, writerDataSource);
        dataSourceMap.put(DataSourceType.READER, readerDataSource);

        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        dataSourceRouter.setTargetDataSources(dataSourceMap);
        dataSourceRouter.setDefaultTargetDataSource(writerDataSource);

        return dataSourceRouter;
    }

    @Primary
    @Bean
    public DataSource lazyDataSource(@Qualifier(DATA_SOURCE_ROUTER) DataSource dataSourceRouter) {
        return new LazyConnectionDataSourceProxy(dataSourceRouter);
    }
}
