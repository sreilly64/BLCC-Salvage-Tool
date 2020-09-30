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
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.entities.PriceData;
import sreilly64.com.github.gw2salvagetool.services.ItemService;

import java.util.List;

@Component
public class PriceDataManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBInitializer.class);
    private RestTemplate restTemplate = new RestTemplateBuilder().build();
    private ItemService itemService;

    @Autowired
    public PriceDataManager(ItemService itemService) {
        this.itemService = itemService;
    }

    @Scheduled(fixedRate = 300000)
    public void updatePriceData(){
        LOGGER.info("begin updating prices now.");

        List<ItemEntity> items = itemService.getAllItems();
        JSONArray prices = getItemPrices(items);
        updateDatabasePrices(prices);
        LOGGER.info("prices updated");
    }

    private void updateDatabasePrices(JSONArray pricesData) {
        for(int i = 0; i < pricesData.length(); i++){
            JSONObject data = pricesData.optJSONObject(i);
            Long itemId = data.optLong("id");
            Integer buyPrice = data.optJSONObject("buys").optInt("unit_price");
            Integer sellPrice = data.optJSONObject("sells").optInt("unit_price");

            PriceData priceData = new PriceData(itemId, buyPrice, sellPrice);
            this.itemService.updatePrices(priceData);
        }
    }

    private JSONArray getItemPrices(List<ItemEntity> items) {
        JSONArray itemPrices = new JSONArray();
        while(!items.isEmpty()){
            //the GW2 API only allows up to 200 items per request
            Integer numItemsPerRequest = Math.min(items.size(), 200);
            //gets the first 200 items from list of all items
            List<ItemEntity> itemsToRequest = items.subList(0, numItemsPerRequest);
            //creates the URI for requesting data on the 200 items
            String uriString = createItemPriceURI(itemsToRequest);
            //updates list of all item IDs
            items.removeAll(itemsToRequest);
            //get the API response for the requested items
            JSONArray priceJSONData = getPriceData(uriString);
            for(int i = 0; i < priceJSONData.length(); i++){
                itemPrices.put(priceJSONData.optJSONObject(i));
            }
        }
        return itemPrices;
    }

    private JSONArray getPriceData(String uriString) {
        String response = restTemplate.getForEntity(uriString, String.class).getBody();
        JSONArray priceData = null;
        try{
            priceData = new JSONArray(response);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return priceData;
    }

    private String createItemPriceURI(List<ItemEntity> itemsToRequest) {
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
