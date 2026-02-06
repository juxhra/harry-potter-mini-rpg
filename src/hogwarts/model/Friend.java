package hogwarts.model;

public class Friend {
    private String name;
    private String house;
    private String quote;
    private int attackPower;

    public Friend(String name, String house, String quote, int attackPower) {
        this.name = name;
        this.house = house;
        this.quote = quote;
        this.attackPower = attackPower;
    }

    public void encourage() {
        System.out.println("[ " + name + " berkata: \"" + quote + "\" ]");
    }

    public String getName() { return name; }
    public String getHouse() { return house; }
    public int getAttackPower() { return attackPower; }
}