# 자바 쿠폰

## 쿠폰 생성, 조회 기능 구현

### 쿠폰

* **쿠폰 이름**
  * [x] 쿠폰 이름은 반드시 존재해야 한다.
  * [x] 길이는 최대 30자 이하여야 한다.
* **할인 금액**
  * [x] 할인 금액은 최소 1,000원 이상, 최대 10,000원 이하다.
  * [x] 할인 금액은 500원 단위로 설정한다.
* **할인율 제약**
  * [ ] 할인율은 할인금액/최소 주문 금액 으로 계산하며 소수점은 버림한다.
  * [ ] 할인율은 3%이상 20%이하이다.
* **최소 주문 금액**
  * [x] 최소 주문 금액은 5,000원 이상 100,000원 이하이다.
* **카테고리**
  * [x] 카테고리는 `패션`, `가전`, `가구`, `식품` 총 네 종류이다.
* **발급 기간**
  * [x] 시작일은 종료일보다 이전이어야 한다.
  * [x] 시작일 00:00:000000 부터 발급가능
  * [x] 종료일 23:59:59:999999 까지 발급가능

### 회원 쿠폰

* [ ] 회원 쿠폰 PK, 쿠폰 PK, 회원 PK, 사용 여부, 발급 일시, 만료 일시 정보를 가진다.
* [ ] 회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능
  * 만료일의 23:59:59:999999 까지 사용 가능

## 복제 지연으로 인한 이슈 확인

* [ ] 테스트 코드로 확인한다.

```java
@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(1000, 10000);
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }
}
```
