package jpabook.jpashop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  //FK갖은쪽을 연관관계 주인으로 정하면되며 주인이 JoinColumn 명시
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  //JPQL 실행 시 N+1 문제우려로 반드시 지연로딩으로 모든ManyToOne, OneToMany 양방향 연관관계들을 설정
  //cascade = CascadeType.ALL : 부모자식관계면 같이 저장
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  //연관관계 메서드
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

  //생성메소드 : 주문 시 배송, 상품정보등 연관되어있기에 별도 메소드화하는걸 권장
  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);

    for(OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }
    //주문 후 현재시간설정
    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());

    return order;
  }

  //주문취소
  public void cancel() {
    //이미 배송되면 취소불가
    if(delivery.getStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송완료되어 취소불가능합니다.");
    }

    this.setStatus(OrderStatus.CANCEL);
    for(OrderItem orderItem : orderItems) {
      orderItem.cancel();
    }
  }

  //전체 주문가격조회로직
  public int getTotalPrice() {
    int totalPrice = 0;
    for(OrderItem orderItem : orderItems) {
      totalPrice += orderItem.getTotalPrice();
    }
    return totalPrice;

    //자바 스트림방식
//    return orderItems.stream()
//        .mapToInt(OrderItem::getTotalPrice)
//        .sum();
  }
}
