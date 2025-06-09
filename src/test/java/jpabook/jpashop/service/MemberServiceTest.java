package jpabook.jpashop.service;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional //JPA 데이터변경 있으면 필수 설정
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("kim");

        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복회원예제() throws Exception {
        Member member = new Member();
        member.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member);
        memberService.join(member2);
//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }

        fail("예외 발생한다.");
    }
}