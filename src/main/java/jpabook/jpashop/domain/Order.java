package jpabook.jpashop.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.weaver.ast.Or;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") //member와 양방향 연관관계
    private Member member;

    //cascade : 매핑된 테이블쪽도 같이 CRUD
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    //1대1 매핑관계 설정 <-> Order
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //자바 8이후부터 JPA와 호환되어 권장하는 시분초 지원객체
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) //반드시 STRING 타입해야 값 보장
    private OrderStatus status; //주문상태: ORDER, CANCEL

    //===연관관계 메서드===//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //주문생성
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //주문취소
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            //배송완료면 취소 불가
            throw new IllegalStateException("이미 배송완료된건 취소가 불가합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);

        for(OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //전체주문가격조회
    public int getTotalPrice() {
        int totalPrice = 0;

        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;

        //자바8 스트림방식
//        return orderItems.stream()
//            .mapToInt(OrderItem::getTotalPrice).sum();
    }
}