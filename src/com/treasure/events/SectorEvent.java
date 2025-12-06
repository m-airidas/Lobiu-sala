package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;

public interface SectorEvent {
    void handle(Player player, Sector sector, int previousRow, int previousCol);
}
