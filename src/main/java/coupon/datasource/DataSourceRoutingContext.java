package coupon.datasource;

public class DataSourceRoutingContext {

    private static final ThreadLocal<Boolean> IS_WRITE_CONTEXT = ThreadLocal.withInitial(() -> false);

    public static void setIsWrite() {
        IS_WRITE_CONTEXT.set(true);
    }

    public static boolean isWrite() {
        return IS_WRITE_CONTEXT.get();
    }

    public static void clear() {
        IS_WRITE_CONTEXT.remove();
    }
}

