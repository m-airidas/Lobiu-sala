package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrapEventTest {

    @Test
    void trapWithoutSuppliesShouldReduceLives() {
        Player player = new Player("Test", 3, 0, 0, 0);
        Sector sector = new Sector(0, 0);
        sector.setTrap(true);

        TrapEvent event = new TrapEvent() {
            @Override
            public void handle(Player p, Sector s, int pr, int pc) {
                p.takeDamage(1);
                s.clearTrap();
            }
        };

        event.handle(player, sector, 0, 0);

        assertEquals(2, player.getLives());
        assertFalse(sector.isTrap());
    }


    @Test
    void trapWithSuppliesShouldUseSupplyButKeepLives() {
        Player player = new Player("Test", 3, 1, 0, 0);
        Sector sector = new Sector(0, 0);
        sector.setTrap(true);

        TrapEvent event = new TrapEvent() {
            @Override
            public void handle(Player p, Sector s, int pr, int pc) {
                p.useSupply();   // panaudojam atsargas
                s.clearTrap();
            }
        };

        event.handle(player, sector, 0, 0);

        assertEquals(3, player.getLives(), "Gyvybės neturi keistis");
        assertEquals(0, player.getSupplies(), "Atsargų turi sumažėti iki 0");
        assertFalse(sector.isTrap(), "Spąstai turi būti išvalyti");
    }

}
