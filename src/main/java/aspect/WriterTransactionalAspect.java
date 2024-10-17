package aspect;

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

    @Before("@annotation(aspect.WriterTransactional)")
    public void switchToWriterDB() {
        DataSourceContextHolder.setDataSourceType(DataSourceType.WRITER);
    }

    @AfterReturning("@annotation(aspect.WriterTransactional)")
    @AfterThrowing("@annotation(aspect.WriterTransactional)")
    public void clearDataSourceAfterExecution() {
        DataSourceContextHolder.clear();
    }
}
