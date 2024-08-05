package coupon.quiz;


import static org.assertj.core.api.Assertions.assertThat;
import static coupon.quiz.QuizHelper.getCoupon;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultipleIssueRequestsTest {

    private static final String BASE_URI = "http://localhost:8080";
    /**
     * 발급 수량 제한이 있는 쿠폰의 아이디
     */
    private static final Long ISSUE_LIMIT_COUPON_ID = 351159L;
    /**
     * 동시에 발급 요청하는 회원의 수
     */
    private static final int NUMBER_OF_MEMBERS = 10;
    /**
     * 회원당 발급 요청하는 쿠폰의 개수
     */
    private static final int COUPON_ISSUE_COUNT_PER_MEMBER = 20;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = BASE_URI;

        RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .put("/coupons/initialize-issue-count/" + ISSUE_LIMIT_COUPON_ID);
    }

    @Test
    void 동시_발급_요청() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger requestCount = new AtomicInteger(0);

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_MEMBERS);
        for (int i = 1; i <= NUMBER_OF_MEMBERS; i++) {  // 회원 번호는 1부터 시작한다.
            int memberId = i;
            executorService.submit(() -> {
                issueCoupon(memberId, requestCount, successCount);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);

        assertThat(successCount.get()).isEqualTo(150);
        assertThat(requestCount.get()).isEqualTo(200);

        Response couponResponse = getCoupon(ISSUE_LIMIT_COUPON_ID);
        long issueCount = couponResponse.body().jsonPath().getLong("issueCount");
        assertThat(issueCount).isEqualTo(150);
    }

    private static void issueCoupon(int memberId, AtomicInteger requestCount, AtomicInteger successCount) {
        for (int count = 0; count < COUPON_ISSUE_COUNT_PER_MEMBER; count++) {
            String requestBody = "{ \"couponId\": " + ISSUE_LIMIT_COUPON_ID + ", \"memberId\": " + memberId + " }";
            Response response = RestAssured.given()
                    .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                    .body(requestBody)
                    .post("/member-coupons")
                    .then()
                    .extract().response();

            requestCount.incrementAndGet();

            if (response.getStatusCode() == HttpStatus.SC_OK) {
                successCount.incrementAndGet();
            }
        }
    }

}
