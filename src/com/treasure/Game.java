package com.treasure;

import com.treasure.events.SectorEvent;
import com.treasure.events.SectorEventFactory;

import java.util.Random;
import java.util.Scanner;

import static com.treasure.GameConfig.*;

public class Game {
    private final MapGrid map;
    private final Player player;
    private final Scanner scanner = new Scanner(System.in);

    public Game(String playerName, int rows, int cols, int lives, int supplies, double trapChance, double enemyChance, double secondaryChance, double memberChance) {
        this.map = new MapGrid(rows, cols);
        this.player = new Player(playerName, lives, supplies, map.getStartRow(), map.getStartCol());

        map.placeTreasure();
        map.placeSecondaryTreasure(secondaryChance);
        map.placeTrapsAndEnemies(trapChance, enemyChance);
        map.placeTeamMembers(memberChance);
    }

    public void start() {
        boolean running = true;
        while (running) {
            map.printVisibleMap(player);
            printStatus();

            String cmd = readCommand();
            System.out.println();
            Sector sector = map.getSector(player.getRow(), player.getCol());
            running = handleCommand(cmd, sector);
        }

            System.out.println("Žaidimas baigtas.");
            System.exit(0);
    }

    private void printStatus() {
        System.out.printf("Vieta: (%d,%d)  Gyvybės: %d  Atsargos: %d%n",
                player.getRow(), player.getCol(), player.getLives(), player.getSupplies());
    }

    private String readCommand() {
        System.out.print("Įvesk komandą: ");
        String cmd = scanner.nextLine().trim().toUpperCase();
        return cmd;
    }

    private boolean handleCommand(String cmd, Sector sector) {
        switch (cmd) {
            case "W": move(-1, 0); break;
            case "S": move(1, 0); break;
            case "D": move(0, 1); break;
            case "A": move(0, -1); break;
            case "STATUS": showStatus(); break;
            case "RECRUIT":
                if (sector.hasTeamMember()) {
                    TeamMemberType[] pool = TeamMemberType.values();
                    TeamMemberType chosen = pool[new Random().nextInt(pool.length)];
                    player.recruit(chosen);
                    sector.clearTeamMember();
                } else {
                    System.out.println("Šiame sektoriuje nėra naujų komandos narių.");
                }
                break;
            case "QUIT":
                System.out.println("Išeini iš žaidimo. Iki!");
                return false;
            default:
                System.out.println("Nežinoma komanda.");
        }

        if (player.getLives() <= 0) {
            System.out.println("Netekai visų gyvybių.");
            return false;
        }

        if (player.foundMainTreasure() && player.isAtStart()) {
            System.out.println("Sveikinu! Radai lobį ir sugrįžai į pradžią gyvas — laimėjai!");
            return false;
        }

        return true;
    }

    private void showStatus() {
        System.out.println(player);
    }

    private void move(int dr, int dc) {
        int prevRow = player.getRow();
        int prevCol = player.getCol();

        int newR = player.getRow() + dr;
        int newC = player.getCol() + dc;
        if (!map.inBounds(newR, newC)) {
            System.out.println("Negali eiti už salos ribų.");
            return;
        }
        player.setPosition(newR, newC);
        Sector sector = map.getSector(newR, newC);

        if (sector.isVisited() && !sector.isStart()) {
            double chance = Math.random();
            if (chance < TRAP_RENEWAL_CHANCE) { // 15%
                double type = Math.random();
                if (type < TYPE_CHANCE) {
                    sector.setDescription("🌪️Pavojus! Atnaujinti spąstai!");
                    sector.setTrap(true);
                } else {
                    sector.setDescription("⚔️Pavojus! Pasirodė sargybinis!");
                    sector.setEnemy(true);
                }
            }
        }

        System.out.println("Įžengi į sektorių: " + sector.getDescription());

        if (player.hasMember(TeamMemberType.PIRATAS)) {
            if (Math.random() < PIRATE_SUPPLY_STEAL_CHANCE && player.getSupplies() > 0) { // 10%
                System.out.println("🏴‍☠️ Piratas pavogė iš tavęs atsargų vienetą!");
                player.useSupply();
            }
        }

        if (player.getSupplies() == 0 && !sector.isStart() && !player.foundMainTreasure()) {
            System.out.println("Nėra atsargų — pavojus padidėja.");
        }

        EventType evt = sector.triggerEvent();
        handleEvent(evt, sector, prevRow, prevCol);
        sector.setVisited(true);
    }

    private void handleEvent(EventType evt, Sector sector, int previousRow, int previousCol) {
      if (sector.isStart() && player.foundMainTreasure()) {
          return;
      }

      SectorEvent eventHandler = SectorEventFactory.create(evt);
      eventHandler.handle(player, sector, previousRow, previousCol);
    }
}
