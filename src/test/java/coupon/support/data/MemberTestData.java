package coupon.support.data;

import coupon.member.domain.Member;

public class MemberTestData {

    public static MemberBuilder defaultMember() {
        return new MemberBuilder()
                .withId(null);
    }

    public static class MemberBuilder {

        private Long id;

        public MemberBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public Member build() {
            return new Member(id);
        }
    }
}
