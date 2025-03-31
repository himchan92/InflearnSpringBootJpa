package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) //매핑할 도메인 설정
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //열걸형은 반드시 STRING 해야한다, 숫자타입은 인덱스처럼 변경시 값 변경되어 장애발생
    private DeliveryStatus status; //READY(배송중), COMP(배송완료)
}
