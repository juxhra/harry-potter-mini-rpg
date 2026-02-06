package hogwarts.npc;

import hogwarts.model.Player;
import java.util.Scanner;

public class DialogSystem {
    private Scanner scanner = new Scanner(System.in);

    public void talkToDumbledore(Player player) {
        System.out.println("\n+---------------------------------------------+");
        System.out.println("| Profesor Dumbledore menatapmu dengan mata   |");
        System.out.println("| berkilau.                                   |");
        System.out.println("| [1] \"Siapa musuh saya sebenarnya?\"          |");
        System.out.println("| [2] \"Bagaimana cara mengalahkannya?\"        |");
        System.out.println("| [3] \"Saya takut... (Dapatkan 1 ramuan)\"     |");
        System.out.print("| Pilih: ");
        int pilih = scanner.nextInt();
        System.out.println("+---------------------------------------------+");
        switch (pilih) {
            case 1 -> System.out.println("\"Ketakutan akan kehilangan membuat kita buta terhadap kebenaran.\" Musuhmu adalah ketakutan itu sendiri... dan Voldemort.\"");
            case 2 -> System.out.println("\"Kekuatan terbesarmu bukan sihir, tapi cinta dan persahabatan.\" Percayalah pada " + player.getHouse() + " dalam dirimu!\"");
            case 3 -> {
                System.out.println("\"Ketakutan itu wajar. Tapi pahlawan sejati bertindak meski takut.\"");
                player.addPotion(1);
            }
            default -> System.out.println("Dumbledore tersenyum bijak.");
        }
    }

    public void talkToHagrid() {
        System.out.println("\n+---------------------------------------------+");
        System.out.println("| Hagrid menepuk punggungmu (agak terlalu     |");
        System.out.println("| keras).                                     |");
        System.out.println("| [1] \"Apa kelemahan Quirrell?\"               |");
        System.out.println("| [2] \"Ada senjata rahasia?\"                  |");
        System.out.println("| [3] \"Cerita dong, Hagrid!\"                  |");
        System.out.print("| Pilih: ");
        int pilih = scanner.nextInt();
        System.out.println("+---------------------------------------------+");
        switch (pilih) {
            case 1 -> System.out.println("\"Dia tak tahan sentuhan cinta! Kalau kamu punya niat tulus, kamu bisa bakar kulitnya!\"");
            case 2 -> System.out.println("\"Pedang Gryffindor muncul waktu dibutuhkan... coba lihat di sekitar!\"");
            case 3 -> System.out.println("\"Dulu aku punya naga... Norbert namanya. Tapi itu cerita lain!\"");
            default -> System.out.println("Hagrid tertawa: \"Kamu anak hebat!\"");
        }
    }
}