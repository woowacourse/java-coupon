package coupon.controller;

import coupon.dto.CouponSaveRequest;
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
        CouponSaveRequest request = new CouponSaveRequest("행운쿠폰", 1000, 30000, "패션", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/coupons")
                .then()
                .statusCode(201);
    }
}
