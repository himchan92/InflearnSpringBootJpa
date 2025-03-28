package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // final 대상 롬복 RequiredArgsConstructor 효과로 DI 처리
    private final EntityManager em;

    //저장
    public void save(Member member) {
        em.persist(member); //영속성 컨텍스트에 저장 : 실제 DB 반영은 아니며 커밋처리해야 반영
    }
    
    //조회 : ID 찾아서 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }
    
    // 전체 조회
    public List<Member> findAll() {
        //JPQL 조회 사용
        //테이블명이 아닌 엔티티 객체명 대상으로 수행
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        //JPQL : 테이블명 아닌 엔티티클래스명 대상 조회
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name) //파라미터 설정
            .getResultList(); //다중조회
    }
}
