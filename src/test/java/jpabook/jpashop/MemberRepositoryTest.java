package jpabook.jpashop;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  @Transactional //JPA 변경작업은 반드시 트랜젝션설정 필수
  public void testMember() throws Exception {
    //given
    Member member = new Member();
    member.setUsername("memberA");

    //when
    Long savedId = memberRepository.save(member);
    Member findMember = memberRepository.find(savedId);

    //then
    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member); //같은 트랜젝션 내에서는 동일 영속성으로 true
  }
}