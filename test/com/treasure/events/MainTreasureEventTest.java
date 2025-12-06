package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTreasureEventTest {

    @Test
    void mainTreasureSetsFlagAndClearsSector() {
        Player player = new Player("Test", 3, 0, 0, 0);
        Sector sector = new Sector(0, 0);
        sector.setMainTreasure(true);

        MainTreasureEvent event = new MainTreasureEvent() {
            @Override
            public void handle(Player p, Sector s, int pr, int pc) {
                p.setFoundMainTreasure(true);
                s.setMainTreasure(false);
            }
        };

        event.handle(player, sector, 0, 0);

        assertTrue(player.foundMainTreasure(), "Žaidėjas turi būti radęs pagrindinį lobį");
        assertFalse(sector.hasMainTreasure(), "Sektorius neturi daugiau turėti pagrindinio lobio");
    }
}
