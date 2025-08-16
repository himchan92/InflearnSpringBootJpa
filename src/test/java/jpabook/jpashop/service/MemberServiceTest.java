package jpabook.jpashop.service;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  EntityManager em;

  @Test
  public void 회원가입() throws Exception {
    Member member = new Member();
    member.setName("kim");

    Long savedId = memberService.join(member);

    em.flush(); //영속성 클리어
    assertEquals(member, memberRepository.findOne(savedId));
  }

  @Test
  public void 중복회원예외() throws Exception {
    Member member1 = new Member();
    member1.setName("kim1");

    Member member2 = new Member();
    member2.setName("kim1");

    memberService.join(member1);

    assertThrows(IllegalStateException.class, () -> memberService.join(member2));
  }
}