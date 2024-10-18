package coupon.coupon.config;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@ExtendWith(MockitoExtension.class)
class DatasourceConfigTest {

    @Mock
    DataSource writeDataSource;

    @Mock
    DataSource readDataSource;

    @InjectMocks
    DatasourceConfig datasourceConfig = new DatasourceConfig();

    @Test
    void routingDataSource_default() {
        // when
        AbstractRoutingDataSource routingDataSource = (AbstractRoutingDataSource)
                datasourceConfig.routingDataSource(writeDataSource, readDataSource);

        routingDataSource.afterPropertiesSet();

        //then
        assertThat(routingDataSource.getResolvedDefaultDataSource()).isEqualTo(writeDataSource);
    }
}
