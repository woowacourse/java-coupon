package coupon.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RoutingDataSourceTest {

    private RoutingDataSource routingDataSource = new RoutingDataSource();

    @Transactional(readOnly = true)
    @Test
    void readOnly() {
        DataSourceType dataSourceType = routingDataSource.determineCurrentLookupKey();
        assertThat(dataSourceType).isEqualTo(DataSourceType.READER);
    }

    @Transactional
    @Test
    void write() {
        DataSourceType dataSourceType = routingDataSource.determineCurrentLookupKey();
        assertThat(dataSourceType).isEqualTo(DataSourceType.WRITER);
    }
}
