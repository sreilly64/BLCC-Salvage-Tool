package sreilly64.com.github.gw2salvagetool.entities;

public class PriceData {

    private Long item_id;
    private Integer buy_price;
    private Integer sell_price;

    public PriceData() {
        this(0L, 0,0);
    }

    public PriceData(Long item_id, Integer buy_price, Integer sell_price) {
        this.item_id = item_id;
        this.buy_price = buy_price;
        this.sell_price = sell_price;
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
}
