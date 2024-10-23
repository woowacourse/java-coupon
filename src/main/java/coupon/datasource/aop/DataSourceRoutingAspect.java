package coupon.datasource.aop;

import coupon.datasource.DataSourceRoutingContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceRoutingAspect {

    @Before("@annotation(coupon.datasource.aop.WriteTransaction)")
    public void setWriteContext() {
        DataSourceRoutingContext.setIsWrite();
    }
}
