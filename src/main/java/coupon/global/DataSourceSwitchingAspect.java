package coupon.global;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/*
@Aspect
@Component
public class DataSourceSwitchingAspect {

    @Around("@annotation(transactional)")
    public Object switchDataSourceIfNeeded(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        boolean readOnly = transactional.readOnly();
        try {
            if (readOnly) {
                DataSourceRouter.setCurrentDataSource(DataSourceType.READER);
            } else {
                DataSourceRouter.setCurrentDataSource(DataSourceType.WRITER);
            }

            Object result = joinPoint.proceed();

            if (readOnly && (result == null || (result instanceof List && ((List<?>) result).isEmpty()))) {
                DataSourceRouter.setCurrentDataSource(DataSourceType.WRITER);
                result = joinPoint.proceed();
            }

            return result;
        } finally {
            DataSourceRouter.clearDataSource();
        }
    }
}
*/


