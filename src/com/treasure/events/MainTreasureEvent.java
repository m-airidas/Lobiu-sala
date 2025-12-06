package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;

public class MainTreasureEvent extends BasicSectorEvent{

    @Override
    public void handle(Player player, Sector sector, int previousRow, int previousCol) {
        System.out.println("🎉 Radai pagrindinį lobį! Dabar sugrįžk į pradžią!");
        player.setFoundMainTreasure(true);
        sector.setMainTreasure(false);
    }
}
