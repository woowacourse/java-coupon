package coupon.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataSourceTypeTest {

    @DisplayName("READER : 트랜잭션 비활성화")
    @Test
    void reader_when_transactionisUnactive() {
        assertAll(
                () -> assertThat(DataSourceType.mapTo(false, true)).isEqualTo(DataSourceType.READER),
                () -> assertThat(DataSourceType.mapTo(false, false)).isEqualTo(DataSourceType.READER)
        );
    }

    @DisplayName("READER : 트랜잭션 활성화 + readOnly 옵션")
    @Test
    void reader_when_transactionisActiveAndReadOnly() {
        assertThat(DataSourceType.mapTo(true, true)).isEqualTo(DataSourceType.READER);
    }

    @DisplayName("Writer : 트랜잭션 활성화 + readOnly 옵션 x")
    @Test
    void writer_when_transactionisActiveAndReadOnlyFalse() {
        assertThat(DataSourceType.mapTo(true, false)).isEqualTo(DataSourceType.WRITER);
    }
}
