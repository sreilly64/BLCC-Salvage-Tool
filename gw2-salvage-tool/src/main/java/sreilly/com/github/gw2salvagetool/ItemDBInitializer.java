package sreilly.com.github.gw2salvagetool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sreilly.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly.com.github.gw2salvagetool.entities.ItemType;
import sreilly.com.github.gw2salvagetool.services.ItemService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ItemDBInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDBInitializer.class);
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    private final ItemService itemService;

    @Autowired
    public ItemDBInitializer(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostConstruct
    public void initializeDatabase(){
        List<Integer> itemIds = fetchItemIds();

        String itemCount = String.format("Total number of items: %s", itemIds.size());
        LOGGER.info(itemCount);

        JSONArray salvageRelatedItemData = getSalvageRelatedItemData(itemIds);

        String salvageRelatedItemCount = String.format("Number of salvage related items: %s", salvageRelatedItemData.length());
        LOGGER.info(salvageRelatedItemCount);

        addSalvageItemsToDB(salvageRelatedItemData);
        LOGGER.info("Item table initilization completed.");
    }

    private void addSalvageItemsToDB(JSONArray salvageRelatedItemData) {
        LOGGER.info("Adding relevant items to DB...");
        for(int i = 0; i < salvageRelatedItemData.length(); i++){
            JSONObject itemData = salvageRelatedItemData.optJSONObject(i);
            //could use a builder here
            Long itemId = itemData.optLong("id");
            String itemName = itemData.optString("name");
            ItemType itemType = ItemType.getEnumByName(itemData.optString("type"));
            String iconURL = itemData.optString("icon");
            Long upgradeId = itemData.optJSONObject("details").optLong("suffix_item_id");

            ItemEntity item = new ItemEntity(itemId, itemName, itemType, iconURL, upgradeId);
            this.itemService.addItem(item);
        }
    }

    public JSONArray getSalvageRelatedItemData(List<Integer> itemIds) {
        LOGGER.info("Filtering items...");
        JSONArray filteredItemsData = new JSONArray();
        while(!itemIds.isEmpty()){
            //the GW2 API only allows up to 200 items per request, so request 200 or however many items are left in the list
            Integer numItemsPerRequest = Math.min(itemIds.size(), 200);
            //gets the first 200 items from list of all IDs
            List<Integer> itemsToRequest = itemIds.subList(0, numItemsPerRequest);
            //creates the URI for requesting data on the 200 item IDs
            String uriString = createItemDataURI(itemsToRequest);
            //updates list of all item IDs
            itemIds.removeAll(itemsToRequest);
            //get the API response for the requested items
            JSONArray itemJsonData = getItemData(uriString);
            //filter data based on item attributes so that we are left with only salvage related items
            JSONArray salvageRelatedItemsData = getSalvageRelatedItems(itemJsonData);
            //add items that weren't filtered out to our global list of salvage related item data
            for(int i = 0; i < salvageRelatedItemsData.length(); i++){
                filteredItemsData.put(salvageRelatedItemsData.optJSONObject(i));
            }
        }
        return filteredItemsData;
    }

    private JSONArray getSalvageRelatedItems(JSONArray itemJsonData) {
        JSONArray filteredItemsData = new JSONArray();
        for(int i = 0; i < itemJsonData.length(); i++){
            JSONObject itemData = itemJsonData.optJSONObject(i);
            //extract the value for the "type" key
            String type = itemData.optString("type");
            //only care about items that can either be salvaged or is the upgrade being recovered from salvaged items
            Boolean isUpgrade = type.equals("UpgradeComponent");
            Boolean isEquipmentWithUpgrade = (type.equals("Weapon") || type.equals("Armor") || type.equals("Trinket")) && itemData.optJSONObject("details").optString("suffix_item_id") != null;
            if(isEquipmentWithUpgrade || isUpgrade){
                filteredItemsData.put(itemData);
            }
        }
        return filteredItemsData;
    }

    private JSONArray getItemData(String uriString) {
        String response = restTemplate.getForEntity(uriString, String.class).getBody();
        JSONArray itemData = null;
        try {
            itemData = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemData;
    }

    public String createItemDataURI(List<Integer> itemIds){
        String uriString = "https://api.guildwars2.com/v2/items?ids=";
        StringBuilder sb = new StringBuilder();
        sb.append(uriString);
        for(Integer id: itemIds){
            sb.append(id.toString());
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    public List<Integer> fetchItemIds(){
        String uriString = "https://api.guildwars2.com/v2/commerce/listings";
        return restTemplate.getForObject(uriString, List.class);
    }
}
