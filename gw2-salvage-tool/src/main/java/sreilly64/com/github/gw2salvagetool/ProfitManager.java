package sreilly64.com.github.gw2salvagetool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.services.ItemService;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProfitManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInitializer.class);
    private List<ItemEntity> profitableItems = new ArrayList<>(20);
    private ItemService itemService;

    @Autowired
    public ProfitManager(ItemService itemService) {
        this.itemService = itemService;
    }

    public List<ItemEntity> getProfitableItems() {
        return profitableItems;
    }

    public void setProfitableItems(List<ItemEntity> profitableItems) {
        this.profitableItems = profitableItems;
    }

    public List<ItemEntity> updateProfitableItems(){
        List<ItemEntity> allItems = itemService.getAllItems();
        return null;
    }
}
