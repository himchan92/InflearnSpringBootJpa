package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속전략 설정
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * 비즈니스로직
     * - 엔티티안에 작성하는 것 권장
     */
    //상품 재고수량 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    //상품 재고수량 감소
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        //수량 없으면 예외처리
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock; //남은수량 대입
    }
}
