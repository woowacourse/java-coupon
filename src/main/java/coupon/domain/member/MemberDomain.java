package coupon.domain.member;

public class MemberDomain {

    private final MemberName memberName;
    
    public MemberDomain(String name) {
        this.memberName = new MemberName(name);
    }

    public String getMemberName() {
        return memberName.getName();
    }
}
