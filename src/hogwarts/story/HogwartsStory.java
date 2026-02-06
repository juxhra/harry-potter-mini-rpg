package hogwarts.story;

import hogwarts.battle.BattleSystem;
import hogwarts.model.*;
import hogwarts.npc.DialogSystem;
import java.util.Scanner;

public class HogwartsStory {
    private Scanner scanner = new Scanner(System.in);
    private BattleSystem battleSystem = new BattleSystem();
    private RandomEvent randomEvent = new RandomEvent();

    public void start() {
        // Ilustrasi awal
        System.out.println(
            "            _\n" +
            "           / \\ \n" +
            "          / _ \\ \n" +
            "         | (_) |\n" +
            "      _  \\___/  _\n" +
            "     / \\       / \\ \n" +
            "    /   \\_____/   \\ \n" +
            "    |             |\n" +
            "    |__/_/_/_/_\\__|\n"
        );

        System.out.println("+---------------------------------------------+");
        System.out.println("| Surat Hogwarts tiba di tanganmu:            |");
        System.out.println("| \"Selamat, kamu diterima di Sekolah Sihir   |");
        System.out.println("| Hogwarts!\"                                 |");
        System.out.println("+---------------------------------------------+");

        Player player = createPlayer();
        Friend[] friends = createFriends(player);

        System.out.println("\n+---------------------------------------------+");
        System.out.println("| Di atas kereta uap, kamu bertemu:           |");
        System.out.println("| " + friends[0].getName() + " dan " + friends[1].getName());
        System.out.println("| Mereka akan menemani petualanganmu!         |");
        System.out.println("+---------------------------------------------+");

        // === SEMUA TAHUN DENGAN SISTEM LENGKAP ===
        startBattlePhase(player, friends, "Prof. Quirrell", "Pengikut Voldemort", 60, 20, "MENARA ASTRONOMI", true);
        startBattlePhase(player, friends, "Tom Riddle", "Jiwa dalam Diary", 70, 22, "KAMAR RAHASIA", false);
        startBattlePhase(player, friends, "Peter Pettigrew", "Si Pengkhianat", 75, 24, "SHRIEKING SHACK", false);
        startBattlePhase(player, friends, "Barty Crouch Jr.", "Penyamar Death Eater", 85, 26, "KUBURAN GELAP", false);
        startBattlePhase(player, friends, "Bellatrix Lestrange", "Tangan Kanan Voldemort", 95, 28, "RUANG RAMALAN", false);
        startBattlePhase(player, friends, "Lord Voldemort", "Dark Lord", 150, 30, "AULA BESAR", false);
    }

    private void startBattlePhase(Player player, Friend[] friends, String enemyName, String role, int hp, int attack, String location, boolean isFirstYear) {
        if (!isFirstYear) {
            System.out.println("\nTekan Enter untuk lanjut...");
            scanner.nextLine();
        }

        // === JUDUL TAHUN ===
        System.out.println("\n+---------------------------------------------+");
        System.out.println("| TAHUN AJARAN " + String.format("%-10s", getYear(enemyName)) + "                   |");
        System.out.println("+---------------------------------------------+");

        // === ILUSTRASI LOKASI SPESIFIK ===
        System.out.println(getLocationIllustration(location));

        // === SINOPSIS LOKASI ===
        showLocationSynopsis(location);

        // === PILIHAN EKSPLORASI ===
        System.out.println("\n[ Pilih cara menjelajahi lokasi: ]");
        String[] paths = getLocationPaths(location);
        for (int i = 0; i < paths.length; i++) {
            System.out.println("[" + (i+1) + "] " + paths[i]);
        }
        System.out.print("Pilihan: ");
        int pathChoice = 1;
        try {
            pathChoice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            pathChoice = 1;
        }

        // === RINTANGAN ===
        applyPathObstacle(player, location, pathChoice);
        if (!player.isAlive()) return;

        // === INTERAKSI NPC ===
        interactBeforeMission(player);
        randomEvent.triggerEvent(player);
        if (!player.isAlive()) return;

        // === SINOPSIS ALASAN PERANG ===
        System.out.println("\n[ " + getBattleReason(enemyName) + " ]");

        // === PERTARUNGAN ===
        Enemy enemy = (enemyName.equals("Lord Voldemort")) 
            ? new Voldemort() 
            : new Enemy(enemyName, role, hp, attack);
        
        // Libatkan kedua teman dalam encourage
        friends[0].encourage();
        friends[1].encourage();
        
        if (!battleSystem.fight(player, friends[0], enemy)) {
            System.exit(0);
        }
    }

    // === ILUSTRASI LOKASI SPESIFIK (DIPANGGIL DI ATAS) ===
    private String getLocationIllustration(String location) {
        return switch (location) {
            case "MENARA ASTRONOMI" -> 
                "    MENARA ASTRONOMI\n" +
                "        .-.\n" +
                "       (   )\n" +
                "      (     )\n" +
                "     (       )\n" +
                "    (         )\n" +
                "   (           )\n" +
                "  /_____________\\\n" +
                " |  *     *     |\n" +
                " |_______________|";
            case "KAMAR RAHASIA" -> 
                "    KAMAR RAHASIA\n" +
                "  _______________\n" +
                " |  |||||||||||  |\n" +
                " |  |  SSSSS  |  |\n" +
                " |  |  S   S  |  |\n" +
                " |  |  SSSSS  |  |\n" +
                " |  |_________|  |\n" +
                " |_______________|";
            case "SHRIEKING SHACK" -> 
                "    SHRIEKING SHACK\n" +
                "       _______\n" +
                "      /       \\\n" +
                "     /  ___    \\\n" +
                "    /  /   \\    \\\n" +
                "   /  |_____|    \\\n" +
                "  /______________\\\n" +
                " |  ~ ~ ~ ~ ~   |\n" +
                " |______________|";
            case "KUBURAN GELAP" -> 
                "    KUBURAN GELAP\n" +
                "   .-.   .-.   .-.\n" +
                "  (   ) (   ) (   )\n" +
                "   '-'   '-'   '-'\n" +
                "  _____ _____ _____\n" +
                " |RIP| |RIP| |RIP|\n" +
                " |___| |___| |___|\n" +
                "     \\  |  |  /\n" +
                "      \\ |  | /\n" +
                "       \\|__|/";
            case "RUANG RAMALAN" -> 
                "    RUANG RAMALAN\n" +
                "  __________________\n" +
                " |  o  o  o  o  o  |\n" +
                " |   o  o  o  o  o |\n" +
                " |  o  o  o  o  o  |\n" +
                " |__________________|\n" +
                "  ~~~~~~~~~~~~~~~~~~";
            case "AULA BESAR" -> 
                "    AULA BESAR HOGWARTS\n" +
                "  ____________________\n" +
                " |                    |\n" +
                " | ====  ====  ====   |\n" +
                " | ====  ====  ====   |\n" +
                " |____________________|\n" +
                "  \\                //\n" +
                "   \\              //\n" +
                "    \\____________//";
            default -> "    LOKASI TIDAK DIKENAL";
        };
    }

    // === SINOPSIS LOKASI (DI BAWAH ILLUSTASI) ===
    private void showLocationSynopsis(String location) {
        System.out.println();
        switch (location) {
            case "MENARA ASTRONOMI" -> 
                System.out.println("Kamu berada di puncak Menara Astronomi. Angin malam berhembus kencang, dan suara bisikan terdengar dari balik pintu terkunci.");
            case "KAMAR RAHASIA" -> 
                System.out.println("Kamu berdiri di depan lorong gelap di bawah Hogwarts. Dinding berlumut, dan suara mendesis ular terdengar dari kejauhan.");
            case "SHRIEKING SHACK" -> 
                System.out.println("Kamu tiba di Shrieking Shack — rumah paling berhantu di Britania. Pintu kayu berderit, dan langit-langit berdebu.");
            case "KUBURAN GELAP" -> 
                System.out.println("Kamu berada di kuburan Little Hangleton. Kabut tebal menyelimuti nisan-nisan tua, dan cahaya bulan memudar.");
            case "RUANG RAMALAN" -> 
                System.out.println("Kamu menyelinap ke Ruang Ramalan di Kementerian Sihir. Ribuan bola kristal berkilauan di rak-rak tinggi.");
            case "AULA BESAR" -> 
                System.out.println("Kamu berdiri di Aula Besar Hogwarts yang porak-poranda. Meja makan hancur, dan api menyala di sudut ruangan.");
        }
    }

    // === ALASAN PERANG (SEBELUM PERTARUNGAN) ===
    private String getBattleReason(String enemyName) {
        return switch (enemyName) {
            case "Prof. Quirrell" -> 
                "Di balik pintu terkunci, kamu menemukan Prof. Quirrell — tapi ada suara Voldemort dari belakang kepalanya! Kamu harus menghentikannya sebelum Batu Bertuah jatuh ke tangan jahat.";
            case "Tom Riddle" -> 
                "Tom Riddle muncul dari diary tua. Dia mengancam akan membunuh Ginny Weasley jika kamu tidak menyerah. Kamu harus melawannya untuk menyelamatkan sahabatmu!";
            case "Peter Pettigrew" -> 
                "Peter Pettigrew mengkhianati orang tuamu. Sekarang dia bersembunyi di Shrieking Shack. Kamu harus mengungkap kebenaran dan menghentikannya!";
            case "Barty Crouch Jr." -> 
                "Barty Crouch Jr. menyamar sebagai Prof. Moody. Dia membawamu ke kuburan untuk membangkitkan Voldemort. Kamu harus melawannya untuk bertahan hidup!";
            case "Bellatrix Lestrange" -> 
                "Bellatrix Lestrange menyiksa Sirius Black di Ruang Ramalan. Kamu tidak bisa membiarkan sahabatmu terbunuh. Inilah saatnya balas dendam!";
            case "Lord Voldemort" -> 
                "Voldemort berdiri di atas puing Aula Besar. Dia menuntut pertarungan terakhir. Dunia sihir bergantung padamu — kamu harus mengalahkannya sekarang atau tidak pernah!";
            default -> "Musuh muncul dari kegelapan. Kamu harus bertarung untuk bertahan hidup!";
        };
    }

    // === METODE LAINNYA (DIPERBARUI) ===
    private String[] getLocationPaths(String location) {
        return switch (location) {
            case "MENARA ASTRONOMI" -> new String[]{
                "Naik tangga spiral berdebu",
                "Gunakan sapu terbang dari menara Quidditch",
                "Ikuti suara bisikan dari lorong"
            };
            case "KAMAR RAHASIA" -> new String[]{
                "Lewat kloset Myrtle yang menangis",
                "Lewat ventilasi rahasia di koridor",
                "Ikuti jejak laba-laba ke hutan"
            };
            case "SHRIEKING SHACK" -> new String[]{
                "Lewat Terowongan Rahasia di Pohon Dilarang",
                "Naik Thestrals dari Hutan Terlarang",
                "Gunakan Peta Perampok untuk cari jalan rahasia"
            };
            case "KUBURAN GELAP" -> new String[]{
                "Lewat kuburan tua dengan cahaya bulan",
                "Ikuti jejak darah di tanah",
                "Gunakan tongkat sebagai penerang"
            };
            case "RUANG RAMALAN" -> new String[]{
                "Naik lift ajaib dari lantai dasar",
                "Lewat lorong penuh cermin ajaib",
                "Ikuti suara ramalan dari kejauhan"
            };
            case "AULA BESAR" -> new String[]{
                "Lewat pintu depan yang hancur",
                "Masuk lewat jendela tinggi",
                "Ikuti cahaya dari Horcrux terakhir"
            };
            default -> new String[]{"Masuk langsung"};
        };
    }

    private void applyPathObstacle(Player player, String location, int choice) {
        System.out.println();
        switch (location) {
            case "MENARA ASTRONOMI" -> {
                switch (choice) {
                    case 1 -> { System.out.println("[Tangga berdebu membuatmu batuk. Kamu kehilangan fokus!]"); player.takeDamage(5); }
                    case 2 -> { System.out.println("[Sapu terbang oleng! Kamu terjatuh dari ketinggian.]"); player.takeDamage(15); }
                    case 3 -> { System.out.println("[Suara bisikan membingungkanmu, tapi kamu tetap waspada.]"); player.takeDamage(8); }
                }
            }
            case "KAMAR RAHASIA" -> {
                switch (choice) {
                    case 1 -> { System.out.println("[Myrtle menangis keras. Kamu terganggu dan jatuh!]"); player.takeDamage(5); }
                    case 2 -> { System.out.println("[Ventilasi sempit penuh debu. Kamu hampir sesak napas!]"); player.takeDamage(10); }
                    case 3 -> { System.out.println("[Laba-laba raksasa menghadang! Kamu kabur dengan luka.]"); player.takeDamage(15); }
                }
            }
            case "SHRIEKING SHACK" -> {
                switch (choice) {
                    case 1 -> { System.out.println("[Akar pohon mencengkeram kakimu!]"); player.takeDamage(8); }
                    case 2 -> { System.out.println("[Thestrals liar! Kamu terlempar dari punggungnya.]"); player.takeDamage(12); }
                    case 3 -> { System.out.println("[Peta menunjukkan jalan aman. Tidak ada rintangan.]"); }
                }
            }
            case "KUBURAN GELAP" -> {
                switch (choice) {
                    case 1 -> { System.out.println("[Kuburan bergoyang! Kamu dikejar tangan mayat.]"); player.takeDamage(10); }
                    case 2 -> { System.out.println("[Jejak darah palsu! Kamu terjebak dalam perangkap.]"); player.takeDamage(18); }
                    case 3 -> { System.out.println("[Tongkatmu menerangi jalan. Kamu hindari bahaya.]"); player.takeDamage(5); }
                }
            }
            case "RUANG RAMALAN" -> {
                switch (choice) {
                    case 1 -> { System.out.println("[Lift rusak! Kamu jatuh beberapa lantai.]"); player.takeDamage(12); }
                    case 2 -> { System.out.println("[Cermin ajaib menipumu! Kamu tersesat lama.]"); player.takeDamage(8); }
                    case 3 -> { System.out.println("[Suara ramalan membimbingmu. Tidak ada rintangan.]"); }
                }
            }
            case "AULA BESAR" -> {
                switch (choice) {
                    case 1 -> { System.out.println("[Puing pintu jatuh menimpamu!]"); player.takeDamage(15); }
                    case 2 -> { System.out.println("[Kamu terpeleset dari jendela!]"); player.takeDamage(10); }
                    case 3 -> { System.out.println("[Cahaya Horcrux membimbingmu. Tidak ada rintangan.]"); }
                }
            }
        }
        if (player.isAlive()) {
            System.out.println("Kamu berhasil melewati rintangan!");
        }
    }

    private Friend[] createFriends(Player player) {
        if (player.getName().equals("Harry Potter")) {
            return new Friend[]{
                new Friend("Hermione Granger", "Gryffindor", "Kamu selalu punya cara keluar dari masalah!", 20),
                new Friend("Ron Weasley", "Gryffindor", "Aku akan selalu mendukungmu, Harry!", 18)
            };
        }
        return switch (player.getHouse()) {
            case "Gryffindor" -> new Friend[]{
                new Friend("Hermione Granger", "Gryffindor", "Kamu selalu punya cara keluar dari masalah!", 20),
                new Friend("Ron Weasley", "Gryffindor", "Aku akan selalu mendukungmu!", 18)
            };
            case "Slytherin" -> new Friend[]{
                new Friend("Regulus Black", "Slytherin", "Bahkan Slytherin bisa memilih kebenaran.", 18),
                new Friend("Theo Nott", "Slytherin", "Aku percaya padamu.", 16)
            };
            case "Hufflepuff" -> new Friend[]{
                new Friend("Cedric Diggory", "Hufflepuff", "Keadilan selalu menang, " + player.getName() + ".", 22),
                new Friend("Hannah Abbott", "Hufflepuff", "Kamu layak dipercaya.", 17)
            };
            case "Ravenclaw" -> new Friend[]{
                new Friend("Luna Lovegood", "Ravenclaw", "Nargles bilang kamu akan menang hari ini!", 15),
                new Friend("Cho Chang", "Ravenclaw", "Kecerdasanmu akan mengalahkan kegelapan.", 19)
            };
            default -> new Friend[]{
                new Friend("Neville Longbottom", "Gryffindor", "Kamu menginspirasi kami semua!", 17),
                new Friend("Ginny Weasley", "Gryffindor", "Aku percaya kamu bisa!", 18)
            };
        };
    }

    private String getYear(String enemyName) {
        return switch (enemyName) {
            case "Prof. Quirrell" -> "PERTAMA";
            case "Tom Riddle" -> "KEDUA";
            case "Peter Pettigrew" -> "KETIGA";
            case "Barty Crouch Jr." -> "KEEMPAT";
            case "Bellatrix Lestrange" -> "KELIMA";
            default -> "KEENAM";
        };
    }

    private void interactBeforeMission(Player player) {
        System.out.println("\n+---------------------------------------------+");
        System.out.println("| Sebelum melanjutkan, kamu ingin...           |");
        System.out.println("| [1] Bicara dengan Dumbledore                |");
        System.out.println("| [2] Tanya Hagrid                            |");
        System.out.println("| [3] Lanjutkan saja                          |");
        System.out.print("| Pilih: ");
        int pilih = scanner.nextInt();
        System.out.println("+---------------------------------------------+");
        DialogSystem dialog = new DialogSystem();
        if (pilih == 1) {
            dialog.talkToDumbledore(player);
        } else if (pilih == 2) {
            dialog.talkToHagrid();
        }
    }

    private Player createPlayer() {
        System.out.println("\n+---------------------------------------------+");
        System.out.println("| [1] Main sebagai Harry Potter               |");
        System.out.println("| [2] Buat karakter sendiri                   |");
        System.out.print("| Pilihan: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("\n+---------------------------------------------+");
            System.out.println("| Selamat datang, Harry Potter!               |");
            System.out.println("| Kamu masuk Gryffindor.                      |");
            System.out.println("+---------------------------------------------+");
            return new Player("Harry Potter", "Gryffindor");
        } else {
            System.out.print("Masukkan namamu: ");
            String name = scanner.nextLine();
            System.out.println("Pilih asrama:");
            System.out.println("[1] Gryffindor\n[2] Slytherin\n[3] Hufflepuff\n[4] Ravenclaw");
            System.out.print("Pilihan (1-4): ");
            int houseChoice = scanner.nextInt();
            scanner.nextLine();

            String house = switch (houseChoice) {
                case 1 -> "Gryffindor";
                case 2 -> "Slytherin";
                case 3 -> "Hufflepuff";
                case 4 -> "Ravenclaw";
                default -> "Gryffindor";
            };
            System.out.println("\n+---------------------------------------------+");
            System.out.println("| Selamat datang, " + String.format("%-18s", name) + " |");
            System.out.println("| Kamu masuk asrama " + String.format("%-16s", house) + " |");
            System.out.println("+---------------------------------------------+");
            return new Player(name, house);
        }
    }
}