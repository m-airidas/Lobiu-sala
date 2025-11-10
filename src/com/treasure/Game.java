package com.treasure;

import java.util.Random;
import java.util.Scanner;

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
            System.out.printf("Vieta: (%d,%d)  Gyvybės: %d  Atsargos: %d%n",
                    player.getRow(), player.getCol(), player.getLives(), player.getSupplies());
            System.out.print("Įvesk komandą: ");
            String cmd = scanner.nextLine().trim().toUpperCase();
            System.out.println();
            Sector sector = map.getSector(player.getRow(), player.getCol());

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
                    running = false;
                    break;
                default:
                    System.out.println("Nežinoma komanda.");
            }

            if (player.getLives() <= 0) {
                System.out.println("Netekai visų gyvybių.");
                running = false;
            }

            if (player.foundMainTreasure() && player.isAtStart()) {
                System.out.println("Sveikinu! Radai lobį ir sugrįžai į pradžią gyvas — laimėjai!");
                running = false;
            }
        }
        System.out.println("Žaidimas baigtas.");
        System.exit(0);
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
            if (chance < 0.15) { // 15%
                double type = Math.random();
                if (type < 0.5) {
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
            if (Math.random() < 0.10 && player.getSupplies() > 0) { // 10%
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
        Random rand = new Random();

        if (sector.isStart() && player.foundMainTreasure()) {
            return;
        }

        switch (evt) {
            case NONE:
                System.out.println("Nieko neįprasto.");
                break;

            case OBSTACLE:
                if (player.getSupplies() > 0) {
                    System.out.println("🚧 Kliūtis! Naudoji atsargas ir prasivalai kelią.");
                    player.useSupply();
                    sector.clearObstacle();
                } else {
                    System.out.println("🚧 Kliūtis! Neturi atsargų — negali praeiti, grįžk atgal.");
                    player.setPosition(previousRow, previousCol);
                }
                break;

            case TRAP:
                if (rand.nextDouble() < 0.65) {
                    if (player.getSupplies() > 0) {
                        System.out.println("💥 Patekai į spąstus, bet panaudoji atsargas ir išsilaisvini!");
                        player.useSupply();
                    } else if (!player.useNomadToSave()) {
                            System.out.println("💥 Patekai į spąstus ir neturi atsargų — prarandi gyvybę!");
                            player.takeDamage(1);
                    }
                } else {
                    System.out.println("⚙️ Spąstai nesuveikė — sėkmė tavo pusėje!");
                }
                sector.clearTrap();
                break;

            case ENEMY:
                if (rand.nextDouble() < 0.6) {
                    System.out.println("⚔️ Susidūrėte su sargybiniu!");
                    if (player.combatWin()) {
                        System.out.println("Laimėjai dvikovą!");
                        if (player.hasMember(TeamMemberType.PIRATAS) && rand.nextDouble() < 0.3) {
                            System.out.println("🏴‍☠️ Piratas atrado grobį ir atnešė atsargų!");
                            player.addSupplies(1);
                        }
                        sector.clearEnemy();
                    } else {
                        System.out.println("Pralaimėjai kovą ir praradai gyvybę.");
                        if (!player.useNomadToSave()) {
                            player.takeDamage(1);
                        }
                    }
                } else {
                    System.out.println("👀 Likai nepastebėtas — gali saugiai praeiti.");
                }
                break;

            case MAIN_TREASURE:
                if (!player.foundMainTreasure()) {
                    System.out.println("🎉 Radai pagrindinį lobį! Dabar sugrįžk į pradžią!");
                    player.setFoundMainTreasure(true);
                } else {
                    System.out.println("Jau radai lobį.");
                }
                break;

            case SECONDARY_TREASURE:
                if (rand.nextDouble() < 0.75) {
                    System.out.println("💰 Radai mažą lobį — papildomos atsargos!");
                    player.addSupplies(1);
                    sector.clearSecondary();
                    if (player.hasMember(TeamMemberType.TYRINĖTOJAS) && rand.nextDouble() < 0.2) {
                        System.out.println("✨ Tyrinėtojas rado papildomų atsargų!");
                        player.addSupplies(1);
                    }
                    sector.clearSecondary();
                } else {
                    System.out.println("💎 Tik blizgantis akmuo — tikras lobis dar kažkur kitur...");
                    sector.clearSecondary();
                }
                break;

            case TEAM_MEMBER:
                System.out.println("🤝 Radai komandos narį!");
                System.out.println("Įvesk RECRUIT, kad prisijungtų prie tavęs.");
                break;
        }
    }
}
