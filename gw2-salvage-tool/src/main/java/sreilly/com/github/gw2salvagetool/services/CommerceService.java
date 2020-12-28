package sreilly.com.github.gw2salvagetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sreilly.com.github.gw2salvagetool.entities.CommerceData;
import sreilly.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly.com.github.gw2salvagetool.repositories.CommerceRepository;
import sreilly.com.github.gw2salvagetool.repositories.ItemRepository;

import java.util.List;

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
                CommerceData upgradeCommerce = this.commerceRepository.findById(item.getUpgradeId()).orElse(null);
                CommerceData itemCommerce = this.commerceRepository.findById(item.getItemId()).orElse(null);
                if(itemCommerce != null && upgradeCommerce != null){
                    String profitString = String.valueOf(Math.round((upgradeCommerce.getSellPrice() - itemCommerce.getBuyPrice()) * 0.85));
                    Integer profit = Integer.parseInt(profitString);
                    itemCommerce.setProfit(profit);
                    this.commerceRepository.save(itemCommerce);
                }
            }
        }
    }

    public void updatePrices(CommerceData commerceDataPoint) {
        this.commerceRepository.save(commerceDataPoint);
    }
}
