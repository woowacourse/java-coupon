package member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	protected Member() {

	}

	public Member(String name) {
		validateName(name);
		this.name = name;
	}

	private void validateName(String name) {
		if(name.length() > 30) {
			throw new IllegalArgumentException("이름은 30자 이하만 가능합니다.");
		}
	}
}
