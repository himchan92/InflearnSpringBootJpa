package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    //주문저장
    public void save(Order order) {
        em.persist(order);
    }

    //주문조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //검색
    //public List<Order> findAll(OrderSearch orderSearch) {}
}
