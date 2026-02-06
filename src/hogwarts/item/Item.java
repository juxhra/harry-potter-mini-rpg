package hogwarts.item;

public class Item {
    public enum Type {
        CLOAK, POTION_FELIX, SWORD, DIARY, STONE, CUP
    }

    private String name;
    private Type type;
    private String description;

    public Item(String name, Type type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "🔮 " + name + ": " + description;
    }
}