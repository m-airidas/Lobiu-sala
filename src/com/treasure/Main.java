package com.treasure;

public class Main {
    public static void main(String[] args) {
        Meniu meniu = new Meniu();

        while (true) {
            int menuChoice = meniu.loadMeniu();
            if (menuChoice == 1) {
                while (true) {
                    int difficulty = meniu.chooseDifficulty();
                    if (difficulty == 4) {
                        System.out.println("Grįžtate į pagrindinį meniu...");
                        System.out.println();
                        break;
                    }
                    int rows = 5, cols = 5;
                    int lives = 3, supplies = 3;
                    double trapChance = 0.2, enemyChance = 0.2;
                    double secondaryChance = 0.15, memberChance = 0.15;

                    switch (difficulty) {
                        case 1: // Lengvas
                            rows = cols = 5;
                            lives = 3;
                            supplies = 5;
                            trapChance = 0.3;
                            enemyChance = 0.35;
                            secondaryChance = 0.15;
                            memberChance = 0.15;
                            break;
                        case 2: // Vidutinis
                            rows = cols = 6;
                            lives = 3;
                            supplies = 3;
                            trapChance = 0.3;
                            enemyChance = 0.45;
                            secondaryChance = 0.15;
                            memberChance = 0.1;
                            break;
                        case 3: // Sunkus
                            rows = cols = 8;
                            lives = 2;
                            supplies = 2;
                            trapChance = 0.3;
                            enemyChance = 0.5;
                            secondaryChance = 0.1;
                            memberChance = 0.15;
                            break;
                    }

                    Game game = new Game("test", rows, cols, lives, supplies, trapChance, enemyChance, secondaryChance, memberChance);
                    game.start();
                }
            }
        }
    }
}