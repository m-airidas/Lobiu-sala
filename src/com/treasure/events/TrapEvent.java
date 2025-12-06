package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;

import static com.treasure.GameConfig.TRAP_TRIGGER_CHANCE;

public class TrapEvent extends BasicSectorEvent {

    @Override
    public void handle(Player player, Sector sector, int previousRow, int previousCol) {
        if (rand.nextDouble() < TRAP_TRIGGER_CHANCE) {
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
    }
}
