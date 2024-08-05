package coupon;

import java.util.concurrent.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@Import(DataSourceJpaConfig.class)
public class CouponApplication implements AsyncConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }

    @Bean(name = "couponTaskExecutor")
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("coupon-");
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setBeanName("couponTaskExecutor");
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }
}
