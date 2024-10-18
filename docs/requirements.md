# 기능 요구사항

## 도메인

- [x] 쿠폰
  - [x] 이름
    - 이름은 반드시 존재해야 한다.
    - 이름의 길이는 최대 30자 이하여야 한다.
  - [x] 할인 금액
    - 할인 금액은 1,000원 이상이어야 한다.
    - 할인 금액은 10,000원 이하여야 한다.
    - 할인 금액은 500원 단위로 설정할 수 있다.
  - [x] 할인율
    - 할인율은 할인금액 / 최소 주문 금액 식을 사용하여 계산하며, 소수점은 버림 한다.
      - 예를 들어, 최소 주문 금액이 30,000원일 때 할인 금액이 1,000원이라면 쿠폰의 할인율은 3%(1000 / 30000 = 3.333...%)가 된다.
    - 할인율은 3% 이상여야 한다.
    - 할인율은 20% 이하여야 한다.
  - [x] 최소 주문 금액
    - 최소 주문 금액은 5,000원 이상이어야 한다.
    - 최소 주문 금액은 100,000원 이하여야 한다.
  - [x] 카테고리
    - 패션
    - 가전
    - 가구
    - 식품
  - [x] 발급 기간
    - 시작일은 종료일보다 이전이어야 한다. 시작일과 종료일이 같다면, 해당 일에만 발급할 수 있는 쿠폰이 된다.
    - 시작일 00:00:00.000000 부터 발급할 수 있다.
    - 종료일 23:59:59.999999 까지 발급할 수 있다.
- [x] 회원 쿠폰
  - [x] 사용 여부
  - [x] 발급 일시
  - [x] 만료 일시
    - 발급일부터 만료일 23:59:59.9999까지 사용가능

## 기능

- [x] 쿠폰 생성 및 조회
  - 조회 기능은 부하 분산을 위해 reader DB의 데이터를 조회해야 한다.
- [x] 복제 지연으로 인한 이슈 확인
- [x] 복제 지연으로 인한 이슈 해결
  - 해당 방법을 사용해서 해결한 이유를 리뷰어가 이해할 수 있도록 설명해야 한다.