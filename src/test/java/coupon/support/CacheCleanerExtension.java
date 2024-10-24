package coupon.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class CacheCleanerExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        CacheCleaner cleaner = SpringExtension.getApplicationContext(context).getBean(CacheCleaner.class);
        cleaner.execute();
    }
}
