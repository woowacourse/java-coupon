package coupon.aspect;

import coupon.config.DataSourceRouter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FallbackWriterAspect {

    @Pointcut("@annotation(coupon.aspect.UseWriter)")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        DataSourceRouter.enableWriterDatabase();
    }

    @After("pointcut()")
    public void after() {
        DataSourceRouter.disableWriterDatabase();
    }
}
