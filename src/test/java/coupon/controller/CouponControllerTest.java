package coupon.controller;

import coupon.dto.request.CouponSaveRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CouponControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setPort() {
        RestAssured.port = port;
    }

    @Test
    void 쿠폰_생성_성공() {
        CouponSaveRequest request = new CouponSaveRequest("행운쿠폰", 1000L, 30000L, "패션", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/coupons")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    void 쿠폰_조회_성공() {
        CouponSaveRequest request = new CouponSaveRequest("행운쿠폰", 1000L, 30000L, "패션", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));

        Long couponId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/coupons")
                .then()
                .statusCode(201)
                .extract()
                .as(Long.class);

        RestAssured.given().log().all()
                .when()
                .get("/coupons/" + couponId)
                .then().log().all()
                .statusCode(200);
    }
}
