package test.restapi;

import test.restapi.member.Grade;
import test.restapi.member.Member;
import test.restapi.member.MemberService;
import test.restapi.member.MemberServiceImpl;

public class MemberApp {
    public static void main(String[] args){
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "aaa", Grade.ADMIN);
        memberService.join(member);

        Member findMember =memberService.findMember(1L);
        System.out.println("new member = "+member.getName());
        System.out.println("findMember = "+findMember.getName());
    }
}
