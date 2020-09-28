package sreilly64.com.github.gw2salvagetool.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @Column(name = "item_id")
    private Long item_id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @Column(name = "icon")
    private String icon;
    @Column(name = "upgrade_id")
    private Long upgrade_id;
    @Column(name = "buy_price")
    private Integer buy_price;
    @Column(name = "sell_price")
    private Integer sell_price;

    public ItemEntity(){
        this(0L, "", null, "", 0l, 0,0);
    }

    public ItemEntity(Long item_id, String name, ItemType type, String icon, Long upgrade_id) {
        this(item_id, name, type, icon, upgrade_id,0,0);
    }

    public ItemEntity(Long item_id, String name, ItemType type, String icon, Long upgrade_id, Integer buy_price, Integer sell_price) {
        this.item_id = item_id;
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.upgrade_id = upgrade_id;
        this.buy_price = buy_price;
        this.sell_price = sell_price;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getUpgrade_id() {
        return upgrade_id;
    }

    public void setUpgrade_id(Long upgrade_id) {
        this.upgrade_id = upgrade_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return item_id.equals(that.item_id) &&
                name.equals(that.name) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item_id, name, type);
    }
}
