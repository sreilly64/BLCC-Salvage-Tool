package sreilly64.com.github.gw2salvagetool.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sreilly64.com.github.gw2salvagetool.entities.ItemEntity;
import sreilly64.com.github.gw2salvagetool.entities.ProfitData;

@Repository
public interface ProfitRepository extends JpaRepository<ProfitData, Long> {
}
