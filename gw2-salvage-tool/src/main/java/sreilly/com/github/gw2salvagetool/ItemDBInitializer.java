package sreilly.com.github.gw2salvagetool;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sreilly.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly.com.github.gw2salvagetool.entities.ItemType;
import sreilly.com.github.gw2salvagetool.services.ItemService;

import javax.annotation.PostConstruct;

@Component
public class ItemDBInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDBInitializer.class);
    private final GW2APIService gw2APIService;
    private final ItemService itemService;

    @Autowired
    public ItemDBInitializer(GW2APIService gw2APIService, ItemService itemService) {
        this.gw2APIService = gw2APIService;
        this.itemService = itemService;
    }

    @PostConstruct
    public void initializeDatabase(){
        JSONArray salvageRelatedItemData = gw2APIService.getSalvageRelatedItemData();

        String salvageRelatedItemCount = String.format("Number of salvage related items: %s", salvageRelatedItemData.length());
        LOGGER.info(salvageRelatedItemCount);

        addSalvageItemsToDB(salvageRelatedItemData);
        LOGGER.info("Item table initilization completed.");
    }

    private void addSalvageItemsToDB(JSONArray salvageRelatedItemData) {
        LOGGER.info("Adding relevant items to DB...");
        for(int i = 0; i < salvageRelatedItemData.length(); i++){
            JSONObject itemData = salvageRelatedItemData.optJSONObject(i);

            ItemEntity item = ItemEntity.builder()
                    .itemId(itemData.optLong("id"))
                    .name(itemData.optString("name"))
                    .type(ItemType.getEnumByName(itemData.optString("type")))
                    .icon(itemData.optString("icon"))
                    .upgradeId(itemData.optJSONObject("details").optLong("suffix_item_id"))
                    .build();
            this.itemService.addItem(item);
        }
    }

}
