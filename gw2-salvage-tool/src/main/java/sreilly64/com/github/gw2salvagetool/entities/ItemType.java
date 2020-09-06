package sreilly64.com.github.gw2salvagetool.entities;

public enum ItemType {

    Weapon("Weapon"), Armor("Armor"), Trinket("Trinket"), UpgradeComponent("UpgradeComponent"), Other("Other");

    private String name;

    public String getName(){
        return this.name;
    }

    private ItemType(String name){
        this.name =  name;
    }

    public static ItemType getEnumByName(String name){
        for(ItemType type : values()){
            if(type.getName().equalsIgnoreCase(name)){
                return type;
            }
        }
        throw new IllegalArgumentException(name + " is not a valid ItemType.");
    }
}
