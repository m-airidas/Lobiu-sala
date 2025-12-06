package com.treasure;

public class DifficultySettings {
    public final int rows;
    public final int cols;
    public final int lives;
    public final int supplies;
    public final double trapChance;
    public final double enemyChance;
    public final double secondaryChance;
    public final double memberChance;

    public DifficultySettings(int rows, int cols, int lives, int supplies,
                              double trapChance, double enemyChance,
                              double secondaryChance, double memberChance) {
        this.rows = rows;
        this.cols = cols;
        this.lives = lives;
        this.supplies = supplies;
        this.trapChance = trapChance;
        this.enemyChance = enemyChance;
        this.secondaryChance = secondaryChance;
        this.memberChance = memberChance;
    }

    public static DifficultySettings easy() {
          return new DifficultySettings(
            GameConfig.EASY_ROWS, GameConfig.EASY_COLS,
            GameConfig.EASY_LIVES, GameConfig.EASY_SUPPLIES,
            GameConfig.EASY_TRAP_CHANCE, GameConfig.EASY_ENEMY_CHANCE,
            GameConfig.EASY_SECONDARY_CHANCE, GameConfig.EASY_MEMBER_CHANCE
          );
    }

    public static DifficultySettings medium() {
        return new DifficultySettings(
                GameConfig.MEDIUM_ROWS, GameConfig.MEDIUM_COLS,
                GameConfig.MEDIUM_LIVES, GameConfig.MEDIUM_SUPPLIES,
                GameConfig.MEDIUM_TRAP_CHANCE, GameConfig.MEDIUM_ENEMY_CHANCE,
                GameConfig.MEDIUM_SECONDARY_CHANCE, GameConfig.MEDIUM_MEMBER_CHANCE
        );
    }

    public static DifficultySettings hard() {
        return new DifficultySettings(
                GameConfig.HARD_ROWS, GameConfig.HARD_COLS,
                GameConfig.HARD_LIVES, GameConfig.HARD_SUPPLIES,
                GameConfig.HARD_TRAP_CHANCE, GameConfig.HARD_ENEMY_CHANCE,
                GameConfig.HARD_SECONDARY_CHANCE, GameConfig.HARD_MEMBER_CHANCE
        );
    }
}
