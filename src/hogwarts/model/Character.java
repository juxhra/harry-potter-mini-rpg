package hogwarts.model;

public abstract class Character {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attackPower;

    public Character(String name, int hp, int attackPower) {
        this.name = name;
        this.maxHp = this.hp = hp;
        this.attackPower = attackPower;
    }

    public abstract void takeDamage(int damage);
    public abstract void heal(int amount);
    public abstract boolean isAlive();

    // TAMBAHAN: Untuk tampilkan status HP setelah diserang
    public String getHealthStatus() {
        return name + " HP: " + hp + "/" + maxHp;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
}