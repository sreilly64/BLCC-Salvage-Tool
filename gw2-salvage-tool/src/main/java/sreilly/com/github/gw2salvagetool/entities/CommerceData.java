package sreilly.com.github.gw2salvagetool.entities;

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
    private Long itemId;
    @Column(name = "buy_price")
    private Integer buyPrice;
    @Column(name = "sell_price")
    private Integer sellPrice;
    @Column(name = "profit")
    private Integer profit;
    @Column(name = "quantity")
    private Integer quantity;

    @Autowired
    public CommerceData(Long itemId, Integer buyPrice, Integer sellPrice, Integer profit, Integer quantity) {
        this.itemId = itemId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.profit = profit;
        this.quantity = quantity;
    }

    public CommerceData(Long itemId, Integer buyPrice, Integer sellPrice, Integer quantity) {
        this(itemId, buyPrice, sellPrice, 0, quantity);
    }

    public CommerceData() {
        this(0L, 0, 0, 0, 0);
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
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
        return itemId.equals(that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
