package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter //실습이해위해 임시 사용이며 평소에는 쓰지말것
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장타입 명시
    private Address address;

    @OneToMany(mappedBy = "member") //Order 테이블 > Member 테이블 매핑 명시
    private List<Order> orders = new ArrayList<>();
}
