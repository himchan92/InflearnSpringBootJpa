package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore //양방향 관계에서는 반대쪽 무조건 걸어줘야 순환참조 방지
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //열거형은 반드시 STRING, ORDINAL 사용 시 항목 추가되면 인덱스 변경되어 오류발생
    private DeliveryStatus status;
}
