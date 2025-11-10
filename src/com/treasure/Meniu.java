package com.treasure;

import java.util.Scanner;

public class Meniu {

    private final Scanner scanner = new Scanner(System.in);

    public int loadMeniu() {
        int choice = 0;
        while (true) {
            //clearConsole();
            System.out.println("=====================\uD83C\uDFDD\uFE0F Lobių sala \uD83C\uDFDD\uFE0F=========================");
            System.out.println("+             Sveikas atvykęs nuotykių ieškotojau!           +");
            System.out.println("+      Rask pagrindinį saloje paslėpta lobį išvengiant       +");
            System.out.println("+      tykančių pavojų ir tapk tikru lobių medžiotoju.       +");
            System.out.println("+                      |1. Žaisti  |                         +");
            System.out.println("+                      |2. Valdymas|                         +");
            System.out.println("+                      |3. Pagalba |                         +");
            System.out.println("+                      |4. Išeiti  |                         +");
            System.out.println("==============================================================");
            System.out.print("Pasirink numerį: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                choice = 0;
            }

            switch (choice) {
                case 1: // Žaisti
                    return 1;
                case 2: // Valdymas
                    showControls();
                    break;
                case 3: // Kaip žaisti
                    showRules();
                    break;
                case 4: // Išeiti
                    System.out.println("Žaidimas baigtas.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Blogai įvesta komanda, bandyk dar kartą.\n");
            }
        }
    }

    public void showControls() {
        clearConsole();
        System.out.println("=============== Valdymas ===============");
        System.out.println("+W - aukštyn                           +");
        System.out.println("+S - žemyn                             +");
        System.out.println("+A - kairėn                            +");
        System.out.println("+D - dešinėn                           +");
        System.out.println("+STATUS - parodyti būklę               +");
        System.out.println("+RECRUIT - surinkti komandos narį      +");
        System.out.println("+QUIT - išeiti iš žaidimo              +");
        System.out.println("========================================");
        System.out.println("Paspausk Enter, kad grįžtum į pagrindinį meniu...");
        scanner.nextLine();
    }

    public void showRules() {
        clearConsole();
        System.out.println("============================= KAIP ŽAISTI ==============================");
        System.out.println("+Tikslas: Surask pagrindinį lobį saloje ir grįžk į pradinį tašką gyvas.+");
        System.out.println("+Judėk po salą, stebėk savo gyvybes ir atsargas.                       +");
        System.out.println("+Susidūrus su spąstais ar priešais, gali prarasti gyvybių.             +");
        System.out.println("+Galima surinkti komandos narius, kad padėtų kovose.                   +");
        System.out.println("+Statusas: Įvesk STATUS, kad pamatytum savo būklę.                     +");
        System.out.println("+Norėdamas išeiti iš žaidimo, įvesk QUIT.                              +");
        System.out.println("========================================================================");
        System.out.println("Paspausk Enter, kad grįžtum į pagrindinį meniu...");
        scanner.nextLine();
    }

    public int chooseDifficulty() {
        int level = 0;
        while (true) {
            System.out.println("\nPasirink sunkumo lygį:");
            System.out.println("1 - Lengvas ");
            System.out.println("2 - Vidutinis ");
            System.out.println("3 - Sunkus ");
            System.out.println("4 - Grįžti ");
            System.out.print("Tavo pasirinkimas: ");

            String input = scanner.nextLine().trim();
            try {
                level = Integer.parseInt(input);
                if (level >= 1 && level <= 4) {
                    break;
                } else {
                    System.out.println("Neteisinga reikšmė. Įveskite 1, 2, 3 arba 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Neteisinga reikšmė. Įveskite skaičių nuo 1 iki 4.");
            }
        }
        return level;
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}

