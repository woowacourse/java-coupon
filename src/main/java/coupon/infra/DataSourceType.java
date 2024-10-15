package coupon.infra;

import lombok.Getter;

@Getter
public enum DataSourceType {
    READER("read"),
    WRITER("write");

    private final String value;

    DataSourceType(String value) {
        this.value = value;
    }
}
