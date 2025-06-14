package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // 롬복 @RequiredArgsConstructor 효과로 final 붙은 필드 생성자 DI 지원
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByName(String name) {
        // JPQL : 대부분 SQL과 유사하나 테이블 대상 아닌 엔티티클래스대상 수행
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name) //파라미터화
            .getResultList(); //목록출력
    }
}
