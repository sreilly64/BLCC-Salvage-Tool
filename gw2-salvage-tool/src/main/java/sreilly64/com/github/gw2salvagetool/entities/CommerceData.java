package sreilly64.com.github.gw2salvagetool.entities;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "commerce")
public class CommerceData {

    @Id
    @Column(name = "item_id")
    private Long item_id;
    @Column(name = "buy_price")
    private Integer buy_price;
    @Column(name = "sell_price")
    private Integer sell_price;
    @Column(name = "profit")
    private Integer profit;
    @Column(name = "quantity")
    private Integer quantity;

    @Autowired
    public CommerceData(Long item_id, Integer buy_price, Integer sell_price, Integer profit, Integer quantity) {
        this.item_id = item_id;
        this.buy_price = buy_price;
        this.sell_price = sell_price;
        this.profit = profit;
        this.quantity = quantity;
    }

    public CommerceData(Long item_id, Integer buy_price, Integer sell_price, Integer quantity) {
        this(item_id, buy_price, sell_price, 0, quantity);
    }

    public CommerceData() {
        this(0L, 0, 0, 0, 0);
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Integer getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(Integer buy_price) {
        this.buy_price = buy_price;
    }

    public Integer getSell_price() {
        return sell_price;
    }

    public void setSell_price(Integer sell_price) {
        this.sell_price = sell_price;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommerceData that = (CommerceData) o;
        return item_id.equals(that.item_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id);
    }
}
