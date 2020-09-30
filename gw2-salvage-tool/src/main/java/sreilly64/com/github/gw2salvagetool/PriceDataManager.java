package sreilly64.com.github.gw2salvagetool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sreilly64.com.github.gw2salvagetool.entities.CommerceData;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.services.ItemService;
import sreilly64.com.github.gw2salvagetool.services.CommerceService;

import java.util.List;

@Component
public class PriceDataManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInitializer.class);
    private RestTemplate restTemplate = new RestTemplateBuilder().build();
    private ItemService itemService;
    private CommerceService commerceService;

    @Autowired
    public PriceDataManager(ItemService itemService, CommerceService commerceService) {
        this.itemService = itemService;
        this.commerceService = commerceService;
    }

    @Scheduled(fixedRate = 300000)
    public void updatePriceData(){
        LOGGER.info("Begin updating prices now.");
        List<ItemEntity> items = itemService.getAllItems();
        JSONArray commerceData = createCommerceDataJSON(items);
        updateCommerceDatabase(commerceData);
        LOGGER.info("Prices updated.");
        this.commerceService.updateProfits();
    }

    private void updateCommerceDatabase(JSONArray commerceData) {
        for(int i = 0; i < commerceData.length(); i++){
            JSONObject data = commerceData.optJSONObject(i);

            Long itemId = data.optLong("id");
            Integer buyPrice = data.optJSONObject("buys").optInt("unit_price");
            Integer sellPrice = data.optJSONObject("sells").optInt("unit_price");
            Integer quantity = data.optJSONObject("sells").optInt("quantity");

            CommerceData commerceDataPoint = new CommerceData(itemId, buyPrice, sellPrice, quantity);

            this.commerceService.updatePrices(commerceDataPoint);
        }
    }

    private JSONArray createCommerceDataJSON(List<ItemEntity> items) {
        JSONArray commerceData = new JSONArray();
        while(!items.isEmpty()){
            //the GW2 API only allows up to 200 items per request
            Integer numItemsPerRequest = Math.min(items.size(), 200);
            //gets the first 200 items from list of all items
            List<ItemEntity> itemsToRequest = items.subList(0, numItemsPerRequest);
            //creates the URI for requesting data on the 200 items
            String uriString = createCommerceURI(itemsToRequest);
            //updates list of all item IDs
            items.removeAll(itemsToRequest);
            //get the API response for the requested items
            JSONArray priceJSONData = getCommerceData(uriString);
            for(int i = 0; i < priceJSONData.length(); i++){
                commerceData.put(priceJSONData.optJSONObject(i));
            }
        }
        return commerceData;
    }

    private JSONArray getCommerceData(String uriString) {
        String response = restTemplate.getForEntity(uriString, String.class).getBody();
        JSONArray commerceData = null;
        try{
            commerceData = new JSONArray(response);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return commerceData;
    }

    private String createCommerceURI(List<ItemEntity> itemsToRequest) {
        String uriString = "https://api.guildwars2.com/v2/commerce/prices?ids=";
        StringBuilder sb = new StringBuilder();
        sb.append(uriString);
        for(ItemEntity item: itemsToRequest){
            sb.append(item.getItem_id().toString());
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

}
