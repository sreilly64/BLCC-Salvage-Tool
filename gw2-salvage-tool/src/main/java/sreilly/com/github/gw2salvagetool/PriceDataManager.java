package sreilly.com.github.gw2salvagetool;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sreilly.com.github.gw2salvagetool.entities.CommerceData;
import sreilly.com.github.gw2salvagetool.services.ItemService;
import sreilly.com.github.gw2salvagetool.services.CommerceService;

import java.util.List;

@Component
public class PriceDataManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceDataManager.class);
    private final GW2APIService gw2APIService;
    private final ItemService itemService;
    private final CommerceService commerceService;

    @Autowired
    public PriceDataManager(GW2APIService gw2APIService, ItemService itemService, CommerceService commerceService) {
        this.gw2APIService = gw2APIService;
        this.itemService = itemService;
        this.commerceService = commerceService;
    }

    @Scheduled(fixedRate = 300000)
    public void updatePriceData(){
        LOGGER.info("Begin updating prices now.");
        List<Long> itemIDs = itemService.getAllDBItemIDs();
        LOGGER.info("Number of item IDs pulled from DB: {}", itemIDs.size());
        JSONArray commerceData = gw2APIService.getCommerceDataJSON(itemIDs);
        updateCommerceDatabase(commerceData);
        LOGGER.info("Prices updated. Begin updating profits");
        this.commerceService.updateProfits();
        LOGGER.info("Profits updated.");
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


}
