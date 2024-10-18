## DB 복제와 캐시

### 미션 설명

행성이가 운영하는 쇼핑몰의 사용자가 점점 늘어나면서 쿠폰 기능을 추가하기로 했다.
최소한의 기능으로 쿠폰을 우선 제공해 보고, 필요에 따라 더 다양한 사용 조건이나 발급 조건을 추가하려고 한다.
최소로 필요한 쿠폰의 요구사항은 다음과 같다.

개발팀에서는 쿠폰 서비스의 고가용성을 위해 두 대의 DB를 사용하기로 했다.
서비스와 데이터의 특성을 고려했을 때 쿠폰과 회원에게 발급된 쿠폰의 양이 다를 것으로 예상되어,
두 데이터의 저장소를 분리하는 작업을 계획 중이다.

### 도메인 설명

#### 회원

회원 정보를 관리한다. 다음 정보를 포함한다.

- 이름
  - 이름은 반드시 존재해야 한다.

#### 쿠폰

회원에게 발급하는 쿠폰의 이름, 할인 금액, 최소 주문 금액 등 쿠폰의 설정에 해당하는 내용을 관리한다.
다음과 같은 제약사항을 갖는다.

- 이름
  - 이름은 반드시 존재해야 한다.
  - 이름 길이는 30자 이하여야 한다.
- 할인 금액
  - 할인 금액 제약
  - 할인 금액은 1,000원 이상이어야 한다.
  - 할인 금액은 10,000원 이하여야 한다.
  - 할인 금액은 500원 단위로 설정할 수 있다.
- 할인율 제약
  - 할인율은 할인금액 / 최소 주문 금액 식을 사용하여 계산하며, 소수점은 버림 한다.
    - 예를 들어, 최소 주문 금액이 30,000원일 때 할인 금액이 1,000원이라면 쿠폰의 할인율은 3%(1000 / 30000 = 3.333...%)가 된다.
  - 할인율은 3% 이상이어야 한다.
  - 할인율은 20% 이하여야 한다.
- 최소 주문 금액
  - 최소 주문 금액은 5,000원 이상이어야 한다.
  - 최소 주문 금액은 100,000원 이하여야 한다.
- 카테고리
  - 서비스의 카테고리는 다음과 같이 네 종류가 있고, 이 중 하나의 카테고리만 선택할 수 있다.
    - 패션
    - 가전
    - 가구
    - 식품
- 발급 기간
  - 시작일은 종료일보다 이전이어야 한다. 시작일과 종료일이 같다면, 해당 일에만 발급할 수 있는 쿠폰이 된다.
  - 시작일 00:00:00.000000 부터 발급할 수 있다.
  - 종료일 23:59:59.999999 까지 발급할 수 있다.

#### 회원 쿠폰

회원에게 발급된 쿠폰 한 장을 관리한다. 다음 정보를 포함한다.

- 회원 쿠폰 PK
- 쿠폰 PK
- 회원 PK
- 사용 여부
- 발급 일시
- 만료 일시
  - 회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다.
  - 만료 일의 23:59:59.999999 까지 사용할 수 있다.

### 프로그래밍 요구사항

- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
  - 기본적으로 Java Style Guide을 원칙으로 한다.
- indent(인덴트, 들여쓰기) depth를 2를 넘지 않도록 구현한다. 1까지만 허용한다.
  - 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
  - 힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은 함수(또는 메서드)를 분리하면 된다.
- 3항 연산자를 쓰지 않는다.
- else 예약어를 쓰지 않는다.
  - else 예약어를 쓰지 말라고 하니 switch/case로 구현하는 경우가 있는데 switch/case도 허용하지 않는다.
  - 힌트: if문에서 값을 반환하는 방식으로 구현하면 else 예약어를 사용하지 않아도 된다.
- 모든 기능에 단위 테스트가 존재해야 한다.
- 함수(또는 메서드)의 길이가 10라인을 넘어가지 않도록 구현한다.
  - 함수(또는 메소드)가 한 가지 일만 하도록 최대한 작게 만들어라.
- 배열 대신 컬렉션을 사용한다.
- 줄여 쓰지 않는다(축약 금지).

---

## 기본 기능 구현 목록

- 도메인 구현
  - [x] 회원: 이름
  - [x] 쿠폰: 쿠폰의 이름, 할인 금액, 최소 주문 금액, 할인율, 카테고리, 발급 기간
  - [x] 회원 쿠폰: 사용 여부, 발급 일시, 만료 일시
- 회원 제약 사항 구현
  - [x] 도메인

## 1차 기능 구현 목록

- 회원 쿠폰 제약 사항 구현
  - [x] 도메인
- 쿠폰 생성, 조회 기능 구현
  - [x] 생성
  - [x] 조회
- [x] 복제 지연으로 인한 이슈 확인
  - 쿠폰을 생성한 후 즉시 조회했을 때 복제 지연으로 인해 데이터 조회에 실패하는 이슈를 확인한다.
- [ ] 복제 지연으로 인한 이슈 해결
  - 쿠폰을 생성했을 때, 의도한 대로 쿠폰이 생성됐는지 검증하도록 수정한다.

