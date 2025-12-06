package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuardEventTest {

    @Test
    void GuardShouldDisappearAfterWinningCombat() {
        Player player = new Player("Test", 3, 0, 0, 0);
        Sector sector = new Sector(0, 0);
        sector.setEnemy(true);

        GuardEvent event = new GuardEvent() {
            @Override
            public void handle(Player p, Sector s, int pr, int pc) {
                s.clearEnemy();
            }
        };

        event.handle(player, sector, 0, 0);

        assertFalse(sector.hasEnemy(), "Priešas turėjo būti pašalintas iš sektoriaus");
    }

    @Test
    void losingCombatShouldReduceLives() {
        Player player = new Player("Test", 3, 0, 0, 0);
        Sector sector = new Sector(0, 0);
        sector.setEnemy(true);

        GuardEvent event = new GuardEvent() {
            @Override
            public void handle(Player p, Sector s, int pr, int pc) {
                p.takeDamage(1);
            }
        };

        event.handle(player, sector, 0, 0);

        assertEquals(2, player.getLives(), "Gyvybės turėjo sumažėti iki 2");
    }
}
