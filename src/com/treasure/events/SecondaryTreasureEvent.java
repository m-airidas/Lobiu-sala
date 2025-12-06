package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;
import com.treasure.TeamMemberType;

import static com.treasure.GameConfig.EXPLORER_SECONDARY_BONUS_CHANCE;
import static com.treasure.GameConfig.SECONDARY_TRIGGER_CHANCE;

public class SecondaryTreasureEvent extends BasicSectorEvent {

    @Override
    public void handle(Player player, Sector sector, int previousRow, int previousCol) {
        if (rand.nextDouble() < SECONDARY_TRIGGER_CHANCE) {
            System.out.println("💰 Radai mažą lobį — papildomos atsargos!");
            player.addSupplies(1);
            sector.clearSecondary();
            if (player.hasMember(TeamMemberType.TYRINĖTOJAS) && rand.nextDouble() < EXPLORER_SECONDARY_BONUS_CHANCE) {
                System.out.println("✨ Tyrinėtojas rado papildomų atsargų!");
                player.addSupplies(1);
            }
            sector.clearSecondary();
        } else {
            System.out.println("💎 Tik blizgantis akmuo — tikras lobis dar kažkur kitur...");
            sector.clearSecondary();
        }
    }
}
