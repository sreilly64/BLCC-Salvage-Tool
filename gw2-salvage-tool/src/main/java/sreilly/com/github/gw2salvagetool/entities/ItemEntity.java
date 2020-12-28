package sreilly.com.github.gw2salvagetool.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @Column(name = "icon")
    private String icon;
    @Column(name = "upgrade_id")
    private Long upgradeId;

    public ItemEntity(){
        this(0L, "", null, "", 0L);
    }

    public ItemEntity(Long itemId, String name, ItemType type, String icon, Long upgradeId) {
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.upgradeId = upgradeId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return itemId.equals(that.itemId) &&
                name.equals(that.name) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, type);
    }
}
