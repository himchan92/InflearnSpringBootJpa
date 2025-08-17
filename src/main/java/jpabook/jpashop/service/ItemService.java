package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional //변경관련이기에 별도 트랜젝션 설정
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  @Transactional
  public Item updateItem(Long itemId, String name, int price, int stockQuantity) {
    Item findItem = itemRepository.findOne(itemId);
    //findItem.change(name, price, stockQuantity) ; -> 아래처럼 나열말고 별도 setter 처리 메소드화 하는 로직 권장(feat. 강사님)
    findItem.setName(name);
    findItem.setPrice(price);
    findItem.setStockQuantity(stockQuantity);
    //save 없이 setter + transactional 효과로 변경감지 일어나 update 수행지원

    return findItem;
  }

  //조회는 클래스전체 readonly = true 트랜젝션 어노테이션 적용받음
  public List<Item> findItems() {
    return itemRepository.findAll();
  }

  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }
}
