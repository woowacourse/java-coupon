package coupon.config;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    private final DataSource writerDataSource;
    private final DataSource readerDataSource;

    protected DatabaseInitializer(
            @Qualifier("writerDataSource") DataSource writerDataSource,
            @Qualifier("readerDataSource") DataSource readerDataSource) {
        this.writerDataSource = writerDataSource;
        this.readerDataSource = readerDataSource;
    }

    @PostConstruct
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        populator.execute(writerDataSource);
        populator.execute(readerDataSource);
    }
}
