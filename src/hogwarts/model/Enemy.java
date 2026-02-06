package hogwarts.model;

public class Enemy extends Character {
    private String role;

    public Enemy(String name, String role, int hp, int attackPower) {
        super(name, hp, attackPower);
        this.role = role;
    }

    @Override
    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
        System.out.println(name + " (" + role + ") menerima " + damage + " damage! HP: " + hp + "/" + maxHp);
    }

    @Override
    public void heal(int amount) {}

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    public String getRole() { return role; }
}