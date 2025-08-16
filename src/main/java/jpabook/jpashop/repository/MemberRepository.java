package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

  @PersistenceContext
  private EntityManager em;

  public void save(Member member) {
    em.persist(member); // DB COMMIT 시점에 DB 반영
  }

  public Member fineOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    //JPQL : 엔티티 대상 from 절 SQL 비슷 수행
    return em.createQuery("select m from Member m", Member.class).getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
  }
}
