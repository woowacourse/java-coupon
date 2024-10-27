package coupon;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDTest {

    // REPEAT TEST 를 통해 1000번 실행해도 전부 통과
    @Test
    @DisplayName("여러 개를 생성해도 무작위를 보장한다.")
    void assure_randomness_with_multiple() {
        final int numberOfUUIDs = 1_000_000;

        final Set<UUID> uuidSet = new HashSet<>();

        for (int i = 0; i < numberOfUUIDs; i++) {
            final UUID uuid = UUID.randomUUID();
            uuidSet.add(uuid);
        }
        assertThat(uuidSet.size()).isEqualTo(numberOfUUIDs);
    }

    @DisplayName("여러 스레드에서 UUID 를 생성해도 무작위를 보장한다.")
    @Test
    void some1() throws InterruptedException {
        final int numberOfUUIDs = 100_000;
        final ThreadLocal<Set<UUID>> threadLocal = ThreadLocal.withInitial(HashSet::new);
        final int numThreads = 50;
        final ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        final Set<UUID> globalUUIDSet = new HashSet<>();
        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                final Set<UUID> uuidSet = threadLocal.get();
                for (int j = 0; j < numberOfUUIDs; j++) {
                    final UUID uuid = UUID.randomUUID();
                    uuidSet.add(uuid);
                }

                synchronized (globalUUIDSet) {
                    globalUUIDSet.addAll(uuidSet);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        assertThat(globalUUIDSet.size()).isEqualTo(numberOfUUIDs * numThreads);
    }
}
