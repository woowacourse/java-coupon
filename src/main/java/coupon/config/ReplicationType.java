package coupon.config;

import java.util.Arrays;
import java.util.function.Predicate;

public enum ReplicationType {

    READ((readOnly) -> true),
    WRITE((readOnly) -> false);

    private final Predicate<Boolean> condition;

    ReplicationType(Predicate<Boolean> condition) {
        this.condition = condition;
    }

    public static ReplicationType from(boolean readOnly) {
        return Arrays.stream(values())
                .filter(replicationType -> replicationType.condition.test(readOnly))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("조건에 맞는 ReplicationType이 존재하지 않습니다."));
    }
}
