package coupon.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanerExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DatabaseCleaner cleaner = SpringExtension.getApplicationContext(context).getBean(DatabaseCleaner.class);
        cleaner.execute();
    }
}
