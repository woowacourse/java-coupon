다음 퀴즈를 풀고, 구글 폼에 제출해 주세요.

### 구글 폼

- 폼 링크: [인프라 강의 퀴즈](https://forms.gle/Kg8F7ds8LwcDPQDm8)
- **폼은 팀 별로 한 사람이 제출**하면 됩니다. 한 팀에서 **중복으로 제출하는 경우, 가장 마지막에 제출한 내용을 사용**합니다.
- 제출한 내용 수정이 가능하므로, 시간이 애매하면 일단 제출하고, 나중에 수정하는 것을 추천합니다.
- 제출 시간 내에 제출한 내용을 이용해서 채점합니다.

### 퀴즈 목록

- [IndexPerformanceTest](src/test/java/coupon/quiz/IndexPerformanceTest.java)
  - Quiz: 아래 5개 테스트에서 호출하는 API의 성능을 향상할 수 있도록 적절한 인덱스를 설정하시오.
    - 각 API에 다른 인덱스를 설정할 수도 있고, 동일한 인덱스를 사용할 수도 있다.
- [MultipleIssueRequestsTest](src/test/java/coupon/quiz/MultipleIssueRequestsTest.java)
  - Quiz: 150개의 발급 수량 제한이 있는 쿠폰을 10명의 사용자가 짧은 시간 동안 20번씩 발급 요청했다. 테스트 실행 후 발견한 문제를 설명하고, 이를 해결하기 위한 방안을 제시하시오.
    - 쿠폰의 발급 수량 제한은 DB에서 관리하며, [Coupon](src/main/java/coupon/coupon/domain/Coupon.java)에서 확인할 수 있습니다.
    - 테스트를 실행할 때마다 발급 수량 제한을 초기화하므로, 별도로 데이터를 수정하거나 테스트를 수정할 필요는 없습니다.
    - **이 퀴즈를 풀기 위해서는 [MemberCouponController](src/main/java/coupon/coupon/ui/MemberCouponController.java)의 네 번째 퀴즈(트랜잭션이 동작하지 않는 문제)를 먼저 풀어야 합니다.**
- [MultipleUseRequestsTest](src/test/java/coupon/quiz/MultipleUseRequestsTest.java)
  - Quiz: 쿠폰을 동시에 사용하려는 요청이 들어온다. 테스트 실행 후 발견한 문제를 설명하고, 이를 해결하기 위한 방안을 제시하시오.
    - 쿠폰의 사용 수량 제한은 DB에서 관리하며, [Coupon](src/main/java/coupon/coupon/domain/Coupon.java)에서 확인할 수 있습니다.
    - 테스트를 실행할 때마다 사용 수량 제한을 초기화하므로, 별도로 데이터를 수정하거나 테스트를 수정할 필요는 없습니다.
- [MemberCouponController](src/main/java/coupon/coupon/ui/MemberCouponController.java)
  - Quiz: 발급 제한 시간 이후에 회원에게 쿠폰이 발급되는 문제가 발생했다. 문제의 원인을 설명하시오.
  - Quiz: 데이터 분석을 위해서 변경 일시를 참고하여 매일 회원이 사용한 쿠폰을 별도의 저장소로 마이그레이션한다. 어제 사용한 쿠폰이 오늘 마이그레이션 되지 않는 문제가 발생했다. 문제의 원인을 설명하시오.
  - Quiz: 마케팅에서 데이터를 분석하던 중, 지난 달에 사용한 쿠폰이 이번 달에 할인 받은 내역으로 반영된 사례가 확인됐다. 문제의 원인을 설명하시오.
  - Quiz: 회원에게 쿠폰을 발급하는 과정에서 트랜잭션이 동작하지 않는 문제가 발생했다. 문제의 해결방법을 설명하시오.
  - Quiz: 회원이 쿠폰을 사용하면, 마케팅에서 활용하기 위해 [MonthlyMemberBenefit](src/main/java/coupon/marketing/domain/MonthlyMemberBenefit.java)에 데이터를 적재한다. 쿠폰을 사용했으나 데이터가 적재되지 않는 문제가 발생했다. 문제의 원인과 해결 방안을 제시하시오.
- 기타
  - 코드에 있는 버그 또는 문제라고 생각하는 내용을 자유롭게 작성하시오.
