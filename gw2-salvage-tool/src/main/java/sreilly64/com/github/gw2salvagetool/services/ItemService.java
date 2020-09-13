package sreilly64.com.github.gw2salvagetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.repositories.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public ItemEntity getItemById(Long item_id) {
        return itemRepository.findById(item_id).get();
    }

    public ItemEntity addItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    public List<ItemEntity> getAllItems() {
        return itemRepository.findAll();
    }
}
