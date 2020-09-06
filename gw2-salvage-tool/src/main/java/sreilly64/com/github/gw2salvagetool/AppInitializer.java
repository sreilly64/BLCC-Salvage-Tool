package sreilly64.com.github.gw2salvagetool;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AppInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializer.class);
    private RestTemplate restTemplate = new RestTemplateBuilder().build();

    @PostConstruct
    public void initializeDatabase(){
        List<Integer> itemIds = fetchItemIds();
        LOGGER.info("Size of all items: " + itemIds.size());
        JSONArray salvageRelatedItemData = getSalvageRelatedItemData(itemIds);
        LOGGER.info("Size of salvage relate items list: " + salvageRelatedItemData.size());
    }

    public JSONArray getSalvageRelatedItemData(List<Integer> itemIds) {
        JSONArray filteredItemsData = new JSONArray();
        while(!itemIds.isEmpty()){
            //the GW2 API only allows up to 200 items per request
            Integer numItemsPerRequest = Math.min(itemIds.size(), 200);
            //gets the first 200 items from list of all IDs
            List<Integer> itemsToRequest = itemIds.subList(0, numItemsPerRequest);
            //creates the URI for requesting data on the 200 item IDs
            String uriString = createItemDataURI(itemsToRequest);
            //updates list of all item IDs
            itemIds.removeAll(itemsToRequest);
            //LOGGER.info(uriString);
            //get the API response for the requested items
            JSONArray itemJsonData = getItemData(uriString);
            //filter data based on item attributes so that we are left with only salvage related items
            JSONArray salvageRelatedItemsData = getSalvageRelatedItems(itemJsonData);
            filteredItemsData.addAll(salvageRelatedItemsData);
        }
        return filteredItemsData;
    }

    private JSONArray getSalvageRelatedItems(JSONArray itemJsonData) {
        JSONArray filteredItemsData = new JSONArray();
        itemJsonData.forEach(item -> {

        });
        return filteredItemsData;
    }

    private JSONArray getItemData(String uriString) {
        JSONArray itemData = restTemplate.getForEntity(uriString, JSONArray.class).getBody();
        //LOGGER.info(itemData.toString());
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
