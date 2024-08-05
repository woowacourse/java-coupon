package coupon.quiz;

import static org.assertj.core.api.Assertions.assertThat;
import static coupon.quiz.QuizHelper.getCoupon;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultipleUseRequestsTest {

    private static final String BASE_URI = "http://localhost:8080";
    /**
     * 발급 수량 제한이 있는 쿠폰의 아이디
     */
    private static final Long USE_LIMIT_COUPON_ID = 351160L;
    /**
     * 동시에 사용 요청하는 스레드의 개수
     */
    private static final int CONCURRENT_REQUEST_COUNT = 5;
    /**
     * 쿠폰을 가진 회원 아이디
     */
    private static final Long MEMBER_ID = 1L;
    /**
     * 회원에게 발급된 회원 쿠폰의 아이디 목록
     */
    private static final List<Long> MEMBER_COUPON_IDS = LongStream.rangeClosed(500001L, 500020L).boxed().toList();

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = BASE_URI;

        RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .put("/coupons/initialize-use-count/" + USE_LIMIT_COUPON_ID);

        RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .body("{\"memberCouponIds\": " + MEMBER_COUPON_IDS + "}")
                .put("/member-coupons/initialize-used");
    }

    @Test
    void 동시_사용_요청() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger requestCount = new AtomicInteger(0);
        AtomicBoolean requestStart = new AtomicBoolean(false);

        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_REQUEST_COUNT);
        for (int i = 0; i < CONCURRENT_REQUEST_COUNT; i++) {
            executorService.submit(() -> useCoupon(requestCount, successCount, requestStart));
        }

        Thread.sleep(1000L);    // 스레드에 실행 요청 후 1초간 대기한 후 요청을 시작하도록 변경한다.
        requestStart.set(true);

        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);

        assertThat(successCount.get()).isEqualTo(5);
        assertThat(requestCount.get()).isEqualTo(100);

        Response couponResponse = getCoupon(USE_LIMIT_COUPON_ID);
        long useCount = couponResponse.body().jsonPath().getLong("useCount");
        assertThat(useCount).isEqualTo(5);
    }

    private static void useCoupon(AtomicInteger requestCount, AtomicInteger successCount, AtomicBoolean requestStart) {
        while (requestStart.get() == false) {
            // 요청을 시작하기 전까지 대기한다.
        }

        for (Long memberCouponId : MEMBER_COUPON_IDS) {
            String requestBody = "{ \"memberCouponId\": " + memberCouponId + ", \"memberId\": " + MEMBER_ID + " }";
            Response response = RestAssured.given()
                    .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                    .body(requestBody)
                    .post("/member-coupons/" + memberCouponId + "/use")
                    .then()
                    .extract().response();

            requestCount.incrementAndGet();

            if (response.getStatusCode() == HttpStatus.SC_OK) {
                successCount.incrementAndGet();
            }
        }
    }
}
