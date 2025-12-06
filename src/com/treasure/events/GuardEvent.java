package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;
import com.treasure.TeamMemberType;

import static com.treasure.GameConfig.GUARD_ENCOUNTER_CHANCE;
import static com.treasure.GameConfig.PIRATE_SUPPLY_BONUS_CHANCE;

public class GuardEvent extends BasicSectorEvent {

    @Override
    public void handle(Player player, Sector sector, int previousRow, int previousCol) {
        if (rand.nextDouble() < GUARD_ENCOUNTER_CHANCE) {
            System.out.println("⚔️ Susidūrėte su sargybiniu!");
            if (player.combatWin()) {
                System.out.println("Laimėjai dvikovą!");
                if (player.hasMember(TeamMemberType.PIRATAS) && rand.nextDouble() < PIRATE_SUPPLY_BONUS_CHANCE) {
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
    }
}
