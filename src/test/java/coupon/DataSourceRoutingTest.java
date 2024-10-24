package coupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class DataSourceRoutingTest {

    private static final String WRITER_DB_PORT = "33306";
    private static final String READER_DB_PORT = "33307";

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName("읽기 전용 트랜잭션이 아니면, Writer DB 데이터소스가 바운딩된다.")
    @Test
    @Transactional(readOnly = false)
    void writeOnlyTransactionTest() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);

        String actual = dataSource.getConnection().getMetaData().getURL();

        assertThat(actual).contains(WRITER_DB_PORT);
    }

    @Test
    @DisplayName("읽기전용 트랜잭션이면 reader DB 데이터소스가 바운딩된다.")
    @Transactional(readOnly = true)
    void readOnlyTransactionTest() throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);

        String actual = dataSource.getConnection().getMetaData().getURL();

        assertThat(actual).contains(READER_DB_PORT);
    }
}
