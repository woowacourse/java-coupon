package coupon.db;

import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public enum DataSourceType {

    READER((isTransactionActive, isReadOnly) -> (!isTransactionActive || isReadOnly)),
    WRITER((isTransactionActive, isReadOnly) -> (isTransactionActive && !isReadOnly)),
    ;

    private final BiPredicate<Boolean, Boolean> sourceCondition;

    DataSourceType(BiPredicate<Boolean, Boolean> sourceCondition) {
        this.sourceCondition = sourceCondition;
    }

    public static DataSourceType mapTo(boolean isTransactionActive, boolean isReadOnly) {
        return Stream.of(values())
                .filter(dataSourceType -> dataSourceType.sourceCondition.test(isTransactionActive, isReadOnly))
                .findAny()
                .orElseThrow(()-> new NoSuchElementException("dataSource를 찾을 수 없습니다."));
    }
}
