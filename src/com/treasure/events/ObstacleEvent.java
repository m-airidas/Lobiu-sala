package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;

public class ObstacleEvent extends BasicSectorEvent {
    @Override
    public void handle(Player player, Sector sector, int previousRow, int previousCol) {
        if (player.getSupplies() > 0) {
            System.out.println("🚧 Kliūtis! Naudoji atsargas ir prasivalai kelią.");
            player.useSupply();
            sector.clearObstacle();
        } else {
            System.out.println("🚧 Kliūtis! Neturi atsargų — negali praeiti, grįžk atgal.");
            player.setPosition(previousRow, previousCol);
        }
    }
}
