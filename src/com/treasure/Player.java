package com.treasure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.treasure.GameConfig.BONUS_ATTACK;
import static com.treasure.GameConfig.COMBAT_WIN_CHANCE;

public class Player {
    private final String name;
    private int lives;
    private int supplies;
    private int row, col;
    private boolean foundMainTreasure = false;
    private final Random rand = new Random();
    private final List<TeamMemberType> teamMembers = new ArrayList<>();
    private int nomadSaves = 0;

    public Player(String name, int lives, int supplies, int startRow, int startCol) {
        this.name = name;
        this.lives = lives;
        this.supplies = supplies;
        this.row = startRow;
        this.col = startCol;
    }

    public String getName() { return name; }
    public int getLives() { return lives; }
    public int getSupplies() { return supplies; }
    public int getRow() { return row; }
    public int getCol() { return col; }

    public void setPosition(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public void takeDamage(int dmg) {
        lives -= dmg;
        if (lives < 0) lives = 0;
    }

    public void addSupplies(int s) {
        supplies += s;
    }

    public void useSupply() {
        if (supplies > 0) supplies--;
    }

    public boolean foundMainTreasure() { return foundMainTreasure; }
    public void setFoundMainTreasure(boolean found) { foundMainTreasure = found; }

    public void recruit(TeamMemberType type) {
            teamMembers.add(type);
            System.out.println(type.name() + " prisijungė prie tavo komandos!");
            if (type == TeamMemberType.KLAJOKLIS) nomadSaves++;
    }

    public boolean hasMember(TeamMemberType t) {
        return teamMembers.contains(t);
    }

    public double getCombatBonus(){
        double bonus = 0.0;
        for (TeamMemberType t : teamMembers) {
            if (t == TeamMemberType.ŠUO) {
                bonus += BONUS_ATTACK;
            }
        }
        return bonus;
    }

    /*public double getExplorerSecondaryBonus() {
        double bonus = 0.0;
        for (TeamMemberType t : teamMembers) {
            if (t == TeamMemberType.TYRINĖTOJAS) {
                bonus += 0.15;
            }
        }
        return bonus;
    }*/

    public boolean useNomadToSave(){
        if (nomadSaves > 0) {
            nomadSaves--;
            teamMembers.remove(TeamMemberType.KLAJOKLIS);
            System.out.println("🛡️ Klajoklis paaukojo save ir išgelbėjo tave!");
            return true;
        }
        return false;
    }

    public boolean isAtStart() {
        return row == 0 && col == 0;
    }

    public boolean combatWin() {
        double base = COMBAT_WIN_CHANCE;
        double bonus = getCombatBonus();
        double chance = Math.min(0.95, base + bonus);
        return rand.nextDouble() < chance;
    }

    @Override
    public String toString() {
        String members = teamMembers.isEmpty() ? "0" : String.join(", ",
                teamMembers.stream().map(Enum::name).toArray(String[]::new));
        return String.format("Žaidėjas: %s | Gyvybės: %d | Atsargos: %d | Komandos nariai: %s | Vieta: (%d,%d) | Lobis: %s",
                name, lives, supplies, members, row, col, foundMainTreasure ? "TAIP" : "NE");
    }
}
