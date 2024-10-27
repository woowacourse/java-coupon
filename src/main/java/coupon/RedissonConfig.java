package coupon;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    //  redis:
    //    image: redis:7.0.12
    //    container_name: redis
    //    restart: always
    //    ports:
    //      - "36379:6379"
    //    networks:
    //      coupon_dock_net:
    //        ipv4_address: 172.20.0.20

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:36379")
                .setDatabase(0);

        return Redisson.create(config);
    }
}
