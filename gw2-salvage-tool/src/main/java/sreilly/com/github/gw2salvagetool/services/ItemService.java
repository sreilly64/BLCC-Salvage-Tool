package sreilly.com.github.gw2salvagetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sreilly.com.github.gw2salvagetool.repositories.ItemRepository;
import sreilly.com.github.gw2salvagetool.entities.ItemEntity;

import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public ItemEntity getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElse(new ItemEntity());
    }

    public ItemEntity addItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }
}
