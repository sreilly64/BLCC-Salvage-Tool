package sreilly.com.github.gw2salvagetool.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sreilly.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly.com.github.gw2salvagetool.services.ItemService;

import java.util.List;

@Controller
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping(value = "/items/{itemId}")
    public ResponseEntity<ItemEntity> getItemById(@PathVariable Long itemId){
        ItemEntity item = null;
        try{
            item = itemService.getItemById(itemId);
        }catch(Exception e){
            LOGGER.info(e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping(value = "/items")
    public ResponseEntity<List<ItemEntity>> getAllItems(){
        List<ItemEntity> itemsList = null;
        try{
            itemsList = itemService.getAllItems();
        }catch(Exception e){
            LOGGER.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(itemsList, HttpStatus.OK);
    }

//    @PostMapping(value = "/items")
//    public ResponseEntity<ItemEntity> addItem(@RequestBody ItemEntity newItem){
//        ItemEntity item = null;
//        try{
//            item = itemService.addItem(newItem);
//        }catch(Exception e){
//            LOGGER.info(e.getMessage(), e);
//            return ResponseEntity.badRequest().build();
//        }
//        return new ResponseEntity<>(item, HttpStatus.OK);
//    }

    //@RequestMapping(value = "/login", method = RequestMethod.OPTIONS)

}
