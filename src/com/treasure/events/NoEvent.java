package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;

public class NoEvent extends BasicSectorEvent{

    @Override
    public void handle(Player player, Sector sector, int previousRow, int previousCol) {
        System.out.println("Nieko neįprasto.");
    }
}
