package hogwarts.story;

import hogwarts.model.Player;
import java.util.Random;
import java.util.Scanner;

public class RandomEvent {
    private Random rand = new Random();
    private Scanner scanner = new Scanner(System.in);

    public void triggerEvent(Player player) {
        System.out.println("\n[ Saat berjalan melewati lorong gelap, sesuatu terjadi... ]");
        int event = rand.nextInt(3);

        switch (event) {
            case 0 -> {
                System.out.println("[ Dobby muncul tiba-tiba dari balik patung! ]");
                System.out.println("\"Dobby tahu tuan butuh bantuan!\"");
                player.addPotion(1);
            }
            case 1 -> {
                System.out.println("[ Bayangan hitam melompat dari langit-langit! ]");
                System.out.println("Kamu diserang dari kejauhan!");
                int damage = 10 + rand.nextInt(15);
                player.takeDamage(damage);
                if (!player.isAlive()) {
                    System.out.println("[ Dunia gelap... Kamu kehilangan kesadaran. ]");
                }
            }
            case 2 -> {
                System.out.println("[ Kamu menemukan gulungan kuno tersembunyi di balik buku. ]");
                System.out.println("\"Voldemort takut pada cinta sejati...\"");
                System.out.println("[ Petunjuk: Musuh akhir lebih lemah jika kamu bermain dengan niat tulus. ]");
            }
        }
        System.out.print("\nTekan Enter untuk lanjut...");
        scanner.nextLine();
    }
}