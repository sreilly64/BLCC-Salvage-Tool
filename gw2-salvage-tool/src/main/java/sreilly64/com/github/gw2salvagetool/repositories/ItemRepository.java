package sreilly64.com.github.gw2salvagetool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
