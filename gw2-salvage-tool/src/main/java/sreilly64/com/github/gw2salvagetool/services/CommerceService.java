package sreilly64.com.github.gw2salvagetool.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sreilly64.com.github.gw2salvagetool.entities.CommerceData;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.repositories.ItemRepository;
import sreilly64.com.github.gw2salvagetool.repositories.CommerceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommerceService {

    private CommerceRepository commerceRepository;
    private ItemRepository itemRepository;

    @Autowired
    public CommerceService(CommerceRepository commerceRepository, ItemRepository itemRepository) {
        this.commerceRepository = commerceRepository;
        this.itemRepository = itemRepository;
    }

    public void updateProfits() {
        List<ItemEntity> items = itemRepository.findAll();
        for(ItemEntity item : items){
            if(item.getType().getName().equals("Armor") || item.getType().getName().equals("Weapon") || item.getType().getName().equals("Trinket")){
                Optional<CommerceData> upgradeOptional = commerceRepository.findById(item.getUpgrade_id());
                CommerceData upgradeCommerce;
                if(upgradeOptional.isPresent()){
                    upgradeCommerce = upgradeOptional.get();
                } else {
                    continue;
                }
                CommerceData itemCommerce = this.commerceRepository.findById(item.getItem_id()).get();
                String profitString = String.valueOf(Math.round((upgradeCommerce.getSell_price() - itemCommerce.getBuy_price()) * 0.85));
                Integer profit = Integer.parseInt(profitString);

                itemCommerce.setProfit(profit);
                this.commerceRepository.save(itemCommerce);
            }
        }
    }

    public void updatePrices(CommerceData commerceDataPoint) {
        this.commerceRepository.save(commerceDataPoint);
    }
}
