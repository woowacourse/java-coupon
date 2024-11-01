package member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import member.domain.Member;

class MemberTest {

	@DisplayName("이름은 30자 이하이여야 한다.")
	@Test
	void validateNameTest() {
		Assertions.assertThatThrownBy(() -> new Member("hogeehogeehogeehogeehogeehogeehogee"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이름은 30자 이하만 가능합니다.");
	}
}
