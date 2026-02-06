package hogwarts.model;

public class Voldemort extends Enemy {
    public Voldemort() {
        super("Lord Voldemort", "Dark Lord", 150, 30);
    }

    @Override
    public void takeDamage(int damage) {
        int reducedDamage = (int)(damage * 0.7);
        this.hp -= reducedDamage;
        if (this.hp < 0) this.hp = 0;
        System.out.println("[Lord Voldemort meringis, tubuhnya menghindari sebagian serangan!]");
        System.out.println("Menerima " + reducedDamage + " damage! HP: " + hp + "/" + maxHp);
    }
}