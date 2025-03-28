package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    //저장
    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item); //상품 없는경우 신규 저장
        } else {
            em.merge(item); // 상품 있으면 갱신
        }
    }

    //단건 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        //JPQL 조회 사용
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
