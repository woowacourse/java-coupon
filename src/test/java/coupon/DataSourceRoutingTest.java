package coupon;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.config.DataSourceType;
import coupon.config.ReplicationRoutingDataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class DataSourceRoutingTest {

    private static final String TEST_METHOD_NAME = "determineCurrentLookupKey";

    @DisplayName("읽기 전용 트랜잭션이 아니면, Writer DB 데이터소스가 바운딩된다.")
    @Test
    @Transactional(readOnly = false)
    void writeOnlyTransactionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();

        Method determineCurrentLookupKey = ReplicationRoutingDataSource.class.getDeclaredMethod(TEST_METHOD_NAME);
        determineCurrentLookupKey.setAccessible(true);

        DataSourceType dataSourceType = (DataSourceType) determineCurrentLookupKey
                .invoke(replicationRoutingDataSource);

        assertThat(dataSourceType).isEqualTo(DataSourceType.WRITER);
    }

    @Test
    @DisplayName("읽기전용 트랜잭션이면 reader DB 데이터소스가 바운딩된다.")
    @Transactional(readOnly = true)
    void readOnlyTransactionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ReplicationRoutingDataSource replicationRoutingDataSource = new ReplicationRoutingDataSource();

        Method determineCurrentLookupKey = ReplicationRoutingDataSource.class.getDeclaredMethod(TEST_METHOD_NAME);
        determineCurrentLookupKey.setAccessible(true);

        DataSourceType dataSourceType = (DataSourceType) determineCurrentLookupKey
                .invoke(replicationRoutingDataSource);

        assertThat(dataSourceType).isEqualTo(DataSourceType.READER);
    }
}
