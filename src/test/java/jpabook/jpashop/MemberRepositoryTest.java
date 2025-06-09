package jpabook.jpashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional //JPA 모든변경작업은 한트랜젝션 내에서 이뤄져야함
    @Rollback(value = false) //기본 롤백이기에 데이터 보고싶으면 설정가능(실무에서는 비추)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long savedId = memberRepository.save(member);

        //then
        Member findMember = memberRepository.find(savedId);

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        //한 트랜젝션내에서는 동일 참조보장
        assertThat(findMember).isEqualTo(member);
        System.out.println("findMember == member " + (findMember == member));
    }
}