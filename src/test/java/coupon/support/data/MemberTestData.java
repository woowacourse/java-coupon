package coupon.support.data;

import coupon.domain.member.Member;

public class MemberTestData {

    public static MemberBuilder defaultMember() {
        return new MemberBuilder()
                .withUsername("testUser")
                .withPassword("testPassword")
                .withEmail("test@test.com");
    }

    public static class MemberBuilder {

        private Long id;
        private String username;
        private String password;
        private String email;

        public MemberBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MemberBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public MemberBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public MemberBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Member build() {
            return new Member(id, username, password, email);
        }
    }
}
