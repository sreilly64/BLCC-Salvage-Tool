package sreilly.com.github.gw2salvagetool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class GW2APIService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GW2APIService.class);
    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public List<Long> getAllItemIds(){
        String uriString = "https://api.guildwars2.com/v2/commerce/listings";
        return restTemplate.getForObject(uriString, List.class);
    }

    public JSONArray getSalvageRelatedItemData() {
        List<Long> itemIds = getAllItemIds();

        LOGGER.info("Total number of items: {}", itemIds.size());

        LOGGER.info("Fetching all item data...");
        JSONArray allItemData = getBulkItemData(itemIds, "https://api.guildwars2.com/v2/items?ids=");
        return getSalvageRelatedItems(allItemData);
    }

    public JSONArray getCommerceDataJSON(List<Long> itemIDs){
        return getBulkItemData(itemIDs, "https://api.guildwars2.com/v2/commerce/prices?ids=");
    }

    public JSONArray getBulkItemData(List<Long> itemIds, String baseUri) {
        JSONArray filteredItemsData = new JSONArray();
        while(!itemIds.isEmpty()){
            //the GW2 API only allows up to 200 items per request, so request 200 or however many items are left in the list
            int numItemsPerRequest = Math.min(itemIds.size(), 200);
            //gets the first 200 items from list of all IDs
            List<Long> itemsToRequest = itemIds.subList(0, numItemsPerRequest);
            //creates the URI for requesting data on the 200 item IDs
            String uriString = createURI(itemsToRequest, baseUri);
            //updates list of all item IDs, removing items that are now being checked
            itemIds.removeAll(itemsToRequest);
            //get the API response for the requested items
            JSONArray itemJsonData = getGW2APIResponse(uriString);
            //add items that weren't filtered out to our global list of salvage related item data
            for(int i = 0; i < itemJsonData.length(); i++){
                filteredItemsData.put(itemJsonData.optJSONObject(i));
            }
        }
        return filteredItemsData;
    }

    private JSONArray getSalvageRelatedItems(JSONArray itemJsonData) {
        LOGGER.info("Filtering salvage related items...");
        //filter data based on item attributes so that we are left with only salvage related items
        JSONArray filteredItems = new JSONArray();
        for(int i = 0; i < itemJsonData.length(); i++){
            JSONObject itemData = itemJsonData.optJSONObject(i);
            //extract the value for the "type" key
            String type = itemData.optString("type");
            //only care about items that can either be salvaged or is the upgrade being recovered from salvaged items
            boolean isUpgrade = type.equals("UpgradeComponent");
            boolean isEquipmentWithUpgrade = (type.equals("Weapon") || type.equals("Armor") || type.equals("Trinket")) && itemData.optJSONObject("details").optString("suffix_item_id") != null;
            if(isEquipmentWithUpgrade || isUpgrade){
                filteredItems.put(itemJsonData.optJSONObject(i));
            }
        }
        return filteredItems;
    }

    private JSONArray getGW2APIResponse(String uriString) {
        String response = restTemplate.getForEntity(uriString, String.class).getBody();
        JSONArray itemData;
        try {
            itemData = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
            itemData = new JSONArray();
        }
        return itemData;
    }


    public String createURI(List<Long> itemIds, String baseUri) {
        StringBuilder sb = new StringBuilder();
        sb.append(baseUri);
        for(Number id: itemIds){
            sb.append(id.toString());
            sb.append(",");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
}
