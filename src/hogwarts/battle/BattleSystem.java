package hogwarts.battle;

import hogwarts.item.Item;
import hogwarts.model.*;
import java.util.Random;
import java.util.Scanner;

public class BattleSystem {
    private Scanner scanner = new Scanner(System.in);
    private Random rand = new Random();

    public boolean fight(Player player, Friend friend, Enemy enemy) {
        System.out.println("\n+---------------------------------------------+");
        System.out.println("| PERTARUNGAN MULAI!                          |");
        System.out.println("| " + player.getName() + " (Level " + player.getLevel() + ") vs " + enemy.getName() + " |");
        System.out.println("+---------------------------------------------+");

        friend.encourage();

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Giliran " + player.getName() + " ---");
            System.out.println("HP: " + player.getHp() + "/" + player.getMaxHp());
            System.out.println("[1] Serang");
            System.out.println("[2] Gunakan Ramuan Penyembuh (" + player.getPotionCount() + ")");
            System.out.println("[3] Minta Bantuan Teman");
            if (!player.getInventory().isEmpty()) {
                System.out.println("[4] Gunakan Item");
            }
            System.out.print("Pilihan: ");

            int choice = 1;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                choice = 1;
            }

            if (choice == 1) {
                String playerSpell = player.getRandomSpell();
                System.out.println("\n" + player.getName() + " berlari maju dengan tekad, menghindari kutukan gelap, lalu berseru: \"" + playerSpell + "!\"");
                enemy.takeDamage(player.getAttackPower());

                if (enemy.isAlive()) {
                    String[] aftermath = {
                        enemy.getName() + " terhuyung mundur, tapi senyum jahatnya tak pernah hilang...",
                        "Debu beterbangan. " + enemy.getName() + " bangkit perlahan, matanya membara...",
                        enemy.getName() + " tertawa keras. \"Kamu akan menyesal sudah menyerangku!\"",
                        "Angin malam berhembus kencang. " + enemy.getName() + " bersiap membalas dendam..."
                    };
                    System.out.println("\n" + aftermath[rand.nextInt(aftermath.length)]);
                }
            } else if (choice == 2) {
                if (player.getPotionCount() > 0) {
                    player.heal(50);
                    
                    // 🔥 SINOPSIS ACAK SAAT MINUM RAMUAN
                    String[] healingScenes = {
                        player.getName() + " dengan cepat meneguk ramuan. Hangatnya menyebar ke seluruh tubuh, memulihkan luka dan kelelahan!",
                        "Dalam sekejap, " + player.getName() + " menarik botol ramuan dari jubahnya dan meminumnya. Tenaga baru mengalir deras!",
                        player.getName() + " berjongkok sejenak, menelan ramuan penyembuh. Napasnya menjadi teratur, luka-lukanya mulai menutup!",
                        "Tangan " + player.getName() + " gemetar, tapi ia berhasil minum ramuan. Kekuatan kembali mengisi tubuhnya yang letih!"
                    };
                    System.out.println("\n" + healingScenes[rand.nextInt(healingScenes.length)]);
                } else {
                    System.out.println("Tidak ada ramuan lagi!");
                }
            } else if (choice == 3) {
                System.out.println("\n" + friend.getName() + " segera membantumu!");
                String friendSpell = getFriendSpell(friend.getName());
                System.out.println(friend.getName() + " berseru: \"" + friendSpell + "!\"");
                enemy.takeDamage(friend.getAttackPower());
                if (enemy.isAlive()) {
                    System.out.println(enemy.getHealthStatus());
                }
            } else if (choice == 4 && !player.getInventory().isEmpty()) {
                useItemInBattle(player);
            } else {
                System.out.println(player.getName() + " ragu sejenak... dan melewatkan giliran.");
            }

            if (enemy.isAlive()) {
                System.out.println("\n--- Giliran " + enemy.getName() + " ---");
                String enemySpell = getEnemySpell(enemy.getName());
                System.out.println("\n" + enemy.getName() + " mengangkat tongkatnya tinggi-tinggi dan meraung: \"" + enemySpell + "!\"");

                player.takeDamage(enemy.getAttackPower());

                if (player.isAlive()) {
                    String[] recovery = {
                        player.getName() + " terjatuh, tapi segera bangkit dengan tatapan penuh tekad!",
                        "Napas " + player.getName() + " memburu, namun ia tak menyerah!",
                        player.getName() + " menggigit bibir, menahan rasa sakit, dan bersiap menyerang lagi!",
                        "Meski terluka, " + player.getName() + " tersenyum. \"Ini belum berakhir!\""
                    };
                    System.out.println("\n" + recovery[rand.nextInt(recovery.length)]);
                }
            }
        }

        if (player.isAlive()) {
            System.out.println("\n+---------------------------------------------+");
            System.out.println("| *** " + player.getName() + " MENANG! ***        |");
            System.out.println("+---------------------------------------------+");
            player.levelUp();
            Item reward = getRewardItem(enemy.getName());
            player.addItem(reward);
            return true;
        } else {
            System.out.println("\n+---------------------------------------------+");
            System.out.println("| " + player.getName() + " dikalahkan...         |");
            System.out.println("| Voldemort menang.                           |");
            System.out.println("+---------------------------------------------+");
            return false;
        }
    }

    // === MANTRA TEMAN ===
    private String getFriendSpell(String friendName) {
        return switch (friendName) {
            case "Hermione Granger" -> "Stupefy";
            case "Cedric Diggory" -> "Protego Totalum";
            case "Luna Lovegood" -> "Riddikulus";
            case "Regulus Black" -> "Expelliarmus";
            case "Neville Longbottom" -> "Herbivicus";
            default -> "Finite Incantatem";
        };
    }

    // === MANTRA MUSUH ===
    private String getEnemySpell(String enemyName) {
        return switch (enemyName) {
            case "Prof. Quirrell" -> "Imperio";
            case "Tom Riddle" -> "Avada Kedavra";
            case "Peter Pettigrew" -> "Crucio";
            case "Barty Crouch Jr." -> "Imperio";
            case "Bellatrix Lestrange" -> "Avada Kedavra";
            case "Lord Voldemort" -> "AVADA KEDAVRA";
            default -> "Kutukan Gelap";
        };
    }

    // === SISTEM ITEM ===
    private void useItemInBattle(Player player) {
        System.out.println("\n+---------------------------------------------+");
        System.out.println("| Pilih item untuk digunakan:                 |");
        var inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("| " + (i + 1) + ". " + inventory.get(i).getName());
        }
        System.out.print("| Pilih (1-" + inventory.size() + "): ");
        try {
            int idx = scanner.nextInt() - 1;
            if (idx >= 0 && idx < inventory.size()) {
                Item item = player.useItem(idx);
                applyItemEffect(player, item);
            } else {
                System.out.println("| Item tidak valid.                           |");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("| Input tidak valid.                          |");
        }
        System.out.println("+---------------------------------------------+");
    }

    private void applyItemEffect(Player player, Item item) {
        switch (item.getType()) {
            case CLOAK -> player.activateCloak();
            case POTION_FELIX -> {
                System.out.println("--> Efek Felix Felicis aktif! Serangan berikutnya jadi KRITIS!");
                player.heal(10);
            }
            default -> System.out.println("Item digunakan, tapi tidak ada efek khusus.");
        }
    }

    private Item getRewardItem(String enemyName) {
        return switch (enemyName) {
            case "Prof. Quirrell" -> new Item("Batu Bertuah", Item.Type.STONE, "Memberi kehidupan abadi (tapi kamu menolaknya).");
            case "Tom Riddle" -> new Item("Buku Diary", Item.Type.DIARY, "Berisi jiwa Voldemort muda.");
            case "Peter Pettigrew" -> new Item("Peta Perampok", Item.Type.CUP, "Menunjukkan setiap gerakan di Hogwarts.");
            case "Barty Crouch Jr." -> new Item("Piala Api", Item.Type.CUP, "Piala yang dulu mengkhianatimu.");
            case "Bellatrix Lestrange" -> new Item("Jubah Tak Terlihat", Item.Type.CLOAK, "Warisan keluarga Potter.");
            default -> new Item("Ramuan Felix Felicis", Item.Type.POTION_FELIX, "Ramuan keberuntungan.");
        };
    }
}