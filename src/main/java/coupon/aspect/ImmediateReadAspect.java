package coupon.aspect;

import coupon.config.DataSourceRouter;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ImmediateReadAspect {

    @Pointcut("@annotation(coupon.aspect.ImmediateRead)")
    public void immediateRead() {
    }

    @Before("immediateRead()")
    public void beforeImmediateReadMode() {
        DataSourceRouter.setImmediateReadMode(true);
    }

    @After("immediateRead()")
    public void afterImmediateReadMode() {
        DataSourceRouter.setImmediateReadMode(false);
    }
}
