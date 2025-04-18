package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/*
    엔티티는 여러 개발자가 변경가능하게 접근된 객체로 API 개발 시 엔티티쪽이 변경되면 안좋다
    - 결론: API 스펙에 맞춘 별도의 DTO를 만들어 활용해야 한다
           엔티티를 파라미터에 셋팅해서 노출시키면 안된다.
 */
@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore // API JSON 결과에서 제외: 안할 시 무조건 노출되기에 불필요한것 셋팅, 양뱡향 연관관계 서로참조로 무한루프 방지
    @OneToMany(mappedBy = "member") //Order 도메인 > Member 매핑
    private List<Order> orders = new ArrayList<>();
}
