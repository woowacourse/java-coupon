package coupon.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DataSourceContextHolder {

    private static final ThreadLocal<DataSourceType> dataSourceContext = new ThreadLocal<>();

    public static DataSourceType getDataSource() {
        return dataSourceContext.get();
    }

    public static void setDataSource(DataSourceType dataSourceType) {
        dataSourceContext.set(dataSourceType);
    }

    public static void clearDataSource() {
        dataSourceContext.remove();
    }
}
