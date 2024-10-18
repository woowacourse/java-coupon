package coupon.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReadWithoutLagAspect {

    @Before("@annotation(coupon.config.ReadWithoutLag)")
    public void readWithoutLag() {
        DataSourceRouter.setDataSourceTypeAsRead();
    }
}
