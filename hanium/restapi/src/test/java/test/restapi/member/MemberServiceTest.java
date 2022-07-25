package test.restapi.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService = new MemberServiceImpl();

    @Test
    void join(){
        //given 준비
        Member member = new Member(1L, "aaa", Grade.ADMIN);

        //when 실행
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        //then 검증
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
