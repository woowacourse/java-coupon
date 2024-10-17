package aspect;

import coupon.config.DataSourceContextHolder;
import coupon.config.DataSourceType;
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
}
