package sreilly64.com.github.gw2salvagetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.repositories.ItemRepository;

import java.util.Optional;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public ResponseEntity<ItemEntity> getItemById(Long item_id) {
        return ResponseEntity.of(itemRepository.findById(item_id));
    }

    public ResponseEntity<ItemEntity> addItem(ItemEntity item) {
        return new ResponseEntity<ItemEntity>(itemRepository.save(item), HttpStatus.CREATED);
    }


}
