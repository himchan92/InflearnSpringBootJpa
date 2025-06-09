package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true) //조회전용 성능최적화
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional //전체가 readOnly = true 라서 별도 설정
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    //최상단 readOnly = true 아래 메소드를 적용됨
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
