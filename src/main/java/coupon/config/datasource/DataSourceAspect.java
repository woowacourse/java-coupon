package coupon.config.datasource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
public class DataSourceAspect {

    @Before("@annotation(coupon.config.datasource.UsingWriterSource)")
    public void setWriterDataSource() {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITER);
    }

    @Before("@annotation(org.springframework.transaction.annotation.Transactional) && !@annotation(coupon.config.datasource.UsingWriterSource)")
    public void setReadDataSource() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            DataSourceContextHolder.setDataSourceType(DataSourceType.READER);
            return;
        }
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITER);
    }
}
