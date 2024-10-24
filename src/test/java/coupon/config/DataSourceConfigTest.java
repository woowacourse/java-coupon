package coupon.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DataSourceConfigTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("writerDataSource")
    private DataSource writerDataSource;

    @Autowired
    @Qualifier("readerDataSource")
    private DataSource readerDataSource;

    @DisplayName("Write Transaction에서 Writer DataSource가 사용된다.")
    @Test
    @Transactional
    void testWriteTransactionUsesWriterDataSource() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            String writeUrl = "jdbc:mysql://localhost:33306";
            assertThat(url).contains(writeUrl);
        }
    }

    @DisplayName("read Transaction에서 Reader DataSource가 사용된다.")
    @Test
    @Transactional(readOnly = true)
    void testReadTransactionUsesReaderDataSource() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            String readUrl = "jdbc:mysql://localhost:33307";
            assertThat(url).contains(readUrl);
        }
    }

    @DisplayName("기본적인 라우팅 설정이 정상적으로 작동하는지 확인한다")
    @Test
    void testRoutingDataSource() throws SQLException {
        assertThat(dataSource).isInstanceOf(LazyConnectionDataSourceProxy.class);

        try (Connection writerConnection = writerDataSource.getConnection()) {
            String writerUrl = writerConnection.getMetaData().getURL();
            assertThat(writerUrl).contains("jdbc:mysql://localhost:33306");
        }

        try (Connection readerConnection = readerDataSource.getConnection()) {
            String readerUrl = readerConnection.getMetaData().getURL();
            assertThat(readerUrl).contains("jdbc:mysql://localhost:33307");
        }
    }
}
