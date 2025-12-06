package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecondaryTreasureEventTest {

    @Test
    void secondaryTreasureAddsSuppliesAndClearsSector() {
        Player player = new Player("Test", 3, 1, 0, 0);
        Sector sector = new Sector(0, 0);
        sector.setSecondaryTreasure(true);

        SecondaryTreasureEvent event = new SecondaryTreasureEvent() {
            @Override
            public void handle(Player p, Sector s, int pr, int pc) {
                p.addSupplies(1);
                s.clearSecondary();
            }
        };

        event.handle(player, sector, 0, 0);

        assertEquals(2, player.getSupplies(), "Atsargos turi padidėti iki 2");
        assertFalse(sector.hasSecondaryTreasure(), "Antrinis lobis turi būti išvalytas");
    }
}
