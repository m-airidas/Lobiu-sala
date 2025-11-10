package com.treasure;

import java.util.Random;

public class MapGrid {
    private final int rows;
    private final int cols;
    private final Sector[][] grid;
    private final int startRow = 0;
    private final int startCol = 0;
    private final Random rand = new Random();

    public MapGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Sector[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Sector(r, c);
            }
        }
        grid[startRow][startCol].setStart(true);
        grid[startRow][startCol].setVisited(true);
    }

    public int getStartRow() { return startRow; }
    public int getStartCol() { return startCol; }

    public void placeTreasure() {
        int tr, tc;
        do {
            tr = rand.nextInt(rows);
            tc = rand.nextInt(cols);
        } while (tr == startRow && tc == startCol);
        grid[tr][tc].setMainTreasure(true);
    }

    public void placeSecondaryTreasure(double secondaryChance) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (rand.nextDouble() < secondaryChance && !grid[r][c].isStart()) {
                    grid[r][c].setSecondaryTreasure(true);
                }
            }
        }
    }

    public void placeTrapsAndEnemies(double trapChance, double enemyChance) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isStart()) continue;
                double x = rand.nextDouble();
                if (x < trapChance) grid[r][c].setTrap(true);
                else if (x < enemyChance) grid[r][c].setEnemy(true);
                else if (x < trapChance + 0.2) grid[r][c].setObstacle(true);
            }
        }
    }

    public void placeTeamMembers(double memberChance) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c].isStart() && Math.random() < memberChance) {
                    grid[r][c].setTeamMember(true);
                }
            }
        }
    }

    public boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public Sector getSector(int r, int c) {
        return grid[r][c];
    }

    public void printVisibleMap(Player p) {
        System.out.println("\nSala (S=start, P=žaidėjas, v=aplankyta, X=kliūtis .=nežinoma):");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (p.getRow() == r && p.getCol() == c) System.out.print("P ");
                else if (grid[r][c].isStart()) System.out.print("S ");
                else if (grid[r][c].isObstacle() && grid[r][c].isVisited()) System.out.print("X ");
                else if (grid[r][c].isVisited()) System.out.print("v ");
                else System.out.print(". ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
