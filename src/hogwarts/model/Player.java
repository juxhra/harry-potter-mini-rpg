package hogwarts.model;

import hogwarts.item.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player extends Character {
    private String house;
    private int level = 1;
    private int potion = 20;  // ✅ DIUBAH DARI 10 → 20
    private List<Item> inventory = new ArrayList<>();
    private boolean cloakActive = false;
    private Random rand = new Random();

    public Player(String name, String house) {
        super(name, 100, 25);
        this.house = house;
    }

    public void levelUp() {
        level++;
        maxHp += 20;
        attackPower += 5;
        System.out.println("\n[ LEVEL UP! Sekarang level " + level + "! ]");
        System.out.println("HP maks: " + maxHp + " | Attack: " + attackPower);
        System.out.println("HP saat ini: " + hp + "/" + maxHp);
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("[ Kamu mendapatkan: " + item.getName() + "! ]");
    }

    public void showInventory() {
        System.out.println("\n[ INVENTARIS " + name + " (Level " + level + "): ]");
        if (inventory.isEmpty()) {
            System.out.println("  - Tidak ada item.");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + inventory.get(i));
            }
        }
        System.out.println("Ramuan Penyembuh: " + potion + " botol");
    }

    public Item useItem(int index) {
        if (index < 0 || index >= inventory.size()) return null;
        return inventory.remove(index);
    }

    public void activateCloak() {
        cloakActive = true;
        System.out.println("[ " + name + " mengaktifkan Jubah Tak Terlihat! ]");
    }

    public boolean isCloakActive() {
        return cloakActive;
    }

    public void deactivateCloak() {
        cloakActive = false;
    }

    public void addPotion(int amount) {
        potion += amount;
        System.out.println("[ Kamu mendapatkan " + amount + " ramuan penyembuh! ]");
    }

    public String getHouse() { return house; }
    public int getLevel() { return level; }
    public int getPotionCount() { return potion; }
    public List<Item> getInventory() { return inventory; }

    @Override
    public void takeDamage(int damage) {
        if (cloakActive) {
            System.out.println("[ Serangan dihindari berkat Jubah Tak Terlihat! ]");
            deactivateCloak();
        } else {
            this.hp -= damage;
            if (this.hp < 0) this.hp = 0;
            System.out.println(name + " menerima " + damage + " damage! HP: " + hp + "/" + maxHp);
        }
    }

    @Override
    public void heal(int amount) {
        if (potion > 0) {
            this.hp += amount;
            if (this.hp > maxHp) this.hp = maxHp;
            potion--;
            System.out.println(name + " menggunakan ramuan! HP: " + hp + "/" + maxHp + " (Ramuan: " + potion + ")");
        } else {
            System.out.println("Tidak ada ramuan lagi!");
        }
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    public String getRandomSpell() {
        List<String> spells = switch (house) {
            case "Gryffindor" -> List.of("Expelliarmus", "Stupefy", "Impedimenta", "Petrificus Totalus");
            case "Slytherin" -> List.of("Serpensortia", "Crucio", "Imperio", "Locomotor Mortis");
            case "Hufflepuff" -> List.of("Protego", "Episkey", "Alohomora", "Glisseo");
            case "Ravenclaw" -> List.of("Revelio", "Wingardium Leviosa", "Lumos Maxima", "Finite Incantatem");
            default -> List.of("Stupefy", "Expelliarmus", "Protego");
        };
        return spells.get(rand.nextInt(spells.size()));
    }
}