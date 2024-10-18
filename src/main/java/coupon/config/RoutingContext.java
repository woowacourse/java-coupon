package coupon.config;

public class RoutingContext {

    public static final ThreadLocal<Boolean> routableToReadSource = ThreadLocal.withInitial(() -> false);

    private RoutingContext() {
    }

    public static void setRoutableToReadSource() {
        routableToReadSource.set(true);
    }

    public static void clear() {
        routableToReadSource.remove();
    }

    public static boolean isRoutableToReadSource() {
        return routableToReadSource.get();
    }
}
