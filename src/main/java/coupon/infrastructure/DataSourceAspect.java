package coupon.infrastructure;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(writerDatabase)")
    public void setWriterDataSource(WriterDatabase writerDatabase) {
        DataSourceRouter.setDataSourceKey(DataSourceType.WRITE);
    }

    @After("@annotation(writerDatabase)")
    public void setReaderDataSource(WriterDatabase writerDatabase) {
        DataSourceRouter.setDataSourceKey(DataSourceType.READ);
    }
}
