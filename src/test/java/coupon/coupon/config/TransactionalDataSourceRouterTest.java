package coupon.coupon.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.transaction.support.TransactionSynchronizationManager;

class TransactionalDataSourceRouterTest {

    private MockedStatic<TransactionSynchronizationManager> mockedTransactionSynchronizationManager;

    @BeforeEach
    void setUpMockTransactionSynchronizationManager() {
        mockedTransactionSynchronizationManager = mockStatic(TransactionSynchronizationManager.class);
    }

    @AfterEach
    void deregisterMockTransactionSynchronizationManager() {
        mockedTransactionSynchronizationManager.close();
    }

    @Test
    void 트랜잭션_read_only_설정이면_읽기_데이터_소스_타입을_반환한다() {
        mockedTransactionSynchronizationManager.when(TransactionSynchronizationManager::isCurrentTransactionReadOnly)
                .thenReturn(true);
        TransactionalDataSourceRouter router = new TransactionalDataSourceRouter();
        assertThat(router.determineCurrentLookupKey()).isEqualTo(DataSourceType.READER);
    }

    @Test
    void 트랜잭션_read_only_설정이_아니면_쓰기_데이터_소스_타입을_반환한다() {
        mockedTransactionSynchronizationManager.when(TransactionSynchronizationManager::isCurrentTransactionReadOnly)
                .thenReturn(false);
        TransactionalDataSourceRouter router = new TransactionalDataSourceRouter();
        assertThat(router.determineCurrentLookupKey()).isEqualTo(DataSourceType.WRITER);
    }
}
