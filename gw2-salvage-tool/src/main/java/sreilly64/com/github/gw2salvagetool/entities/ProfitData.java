package sreilly64.com.github.gw2salvagetool.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "profits")
public class ProfitData {

    @Id
    @Column(name = "item_id")
    private Long item_id;
    @Column(name = "profit")
    private Integer profit;

    public ProfitData() {
        this(0L, 0);
    }

    public ProfitData(Long item_id, Integer profit) {
        this.item_id = item_id;
        this.profit = profit;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitData that = (ProfitData) o;
        return Objects.equals(item_id, that.item_id) &&
                Objects.equals(profit, that.profit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id, profit);
    }
}
