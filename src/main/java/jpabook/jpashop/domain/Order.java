package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") //member와 양방향 연관관계
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne //1대1 매핑관계 설정 <-> Order
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //자바 8이후부터 JPA와 호환되어 권장하는 시분초 지원객체
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING) //반드시 STRING 타입해야 값 보장
    private OrderStatus status; //주문상태: ORDER, CANCEL
}