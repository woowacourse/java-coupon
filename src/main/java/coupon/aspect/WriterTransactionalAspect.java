package coupon.aspect;

import coupon.config.DataSourceContextHolder;
import coupon.config.DataSourceType;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WriterTransactionalAspect {

    @Before("@annotation(coupon.aspect.WriterTransactional)")
    public void switchToWriterDB() {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITER);
    }

    @AfterReturning("@annotation(coupon.aspect.WriterTransactional)")
    @AfterThrowing("@annotation(coupon.aspect.WriterTransactional)")
    public void clearDataSourceAfterExecution() {
        DataSourceContextHolder.clear();
    }
}
