package coupon.support;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseCleanerExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DatabaseCleaner cleaner = SpringExtension.getApplicationContext(context).getBean(DatabaseCleaner.class);
        cleaner.execute();
    }
}
