package sreilly64.com.github.gw2salvagetool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.entities.ProfitData;
import sreilly64.com.github.gw2salvagetool.repositories.ItemRepository;
import sreilly64.com.github.gw2salvagetool.repositories.ProfitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfitService {

    private ProfitRepository profitRepository;
    private ItemRepository itemRepository;

    @Autowired
    public ProfitService(ProfitRepository profitRepository, ItemRepository itemRepository) {
        this.profitRepository = profitRepository;
        this.itemRepository = itemRepository;
    }

    public void updateProfits() {
        List<ItemEntity> items = itemRepository.findAll();
        for(ItemEntity item : items){
            if(item.getType().getName().equals("Armor") || item.getType().getName().equals("Weapon") || item.getType().getName().equals("Trinket")){
                Optional<ItemEntity> upgradeOptional = itemRepository.findById(item.getUpgrade_id());
                ItemEntity upgrade;
                if(upgradeOptional.isPresent()){
                    upgrade = upgradeOptional.get();
                } else {
                    continue;
                }
                String profitString = String.valueOf(Math.round((upgrade.getSell_price() - item.getBuy_price()) * 0.85));
                Integer profit = Integer.parseInt(profitString);
                ProfitData profitData = new ProfitData(item.getItem_id(), profit);
                this.profitRepository.save(profitData);
            }
        }
    }

}
