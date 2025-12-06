package com.treasure;

public class Launcher {

    private final Meniu menu = new Meniu();

    public void run() {
        while (true){
            int choice = menu.loadMeniu();

            if (choice == 1) {
                startLoop();
            }
        }
    }

    private void startLoop() {
     while (true) {
         int difficulty = menu.chooseDifficulty();

         if (difficulty == 4) {
             System.out.println("Grįžtate į pagrindinį meniu...");
             System.out.println();
             return;
         }

         DifficultySettings ds = switch (difficulty) {
             case 1 -> DifficultySettings.easy();
             case 2 -> DifficultySettings.medium();
             case 3 -> DifficultySettings.hard();
             default -> DifficultySettings.easy();
         };

         Game game = new Game(
                 "Test", ds.rows, ds.cols, ds. lives, ds.supplies,
                 ds.trapChance, ds.enemyChance, ds.secondaryChance, ds.memberChance
         );

         game.start();
       }
    }
}
