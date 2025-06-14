package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty //@Valid 대상으로 빈값여부 체크수행
    private String name;

    @Embedded
    @JsonIgnore
    private Address address;

    @JsonIgnore //API 호출결과에서 제외 : 불필요한 내용 정리위함
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
