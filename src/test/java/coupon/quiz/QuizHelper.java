package coupon.quiz;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

public class QuizHelper {

    static Response getCoupon(Long couponId) {
        return RestAssured.given()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .get("/coupons/" + couponId)
                .then()
                .extract().response();
    }
}
