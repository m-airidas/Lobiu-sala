package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;

import java.util.Random;

public abstract class BasicSectorEvent implements SectorEvent {
    protected final Random rand = new Random();

    @Override
    public abstract void handle(Player player, Sector sector, int previousRow, int previousCol);
}
