package sreilly64.com.github.gw2salvagetool.entities;

public enum ItemType {

    WEAPON("weapon"), ARMOR("armor"), TRINKET("trinket"), UPGRADE("upgrade_component");

    private String name;

    public String getName(){
        return this.name;
    }

    private ItemType(String name){
        this.name =  name;
    }
}
