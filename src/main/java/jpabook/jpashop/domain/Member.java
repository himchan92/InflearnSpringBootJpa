package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter //예시를 위해 넣었지만 실무에서는 쓰지말고 별도 비즈니스메소드통해 변경되게 해야함.
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded
    private Address address;

    //mappedBy: 상대방 매핑되있는것 명시하고 읽기전용
    @JsonIgnore //연관관계 반대쪽 설정하여 순환참조 방지
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
