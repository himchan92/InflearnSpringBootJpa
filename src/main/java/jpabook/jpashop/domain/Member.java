package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter //실습 이해를 위해 임시작성한거지 실무에서는 금지!!
public class Member {

  @Id @GeneratedValue
  @Column(name = "member_id") //엔티티명_id로 Pk 만들어야 쉽게 알아보기에 권장
  private Long id;

  private String name;

  @Embedded //내장타입 명시
  private Address address;

  //연관관계 주인아닌쪽에 mappedBy 명시
  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
  private List<Order> orders = new ArrayList<>();
}
