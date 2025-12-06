package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;
import com.treasure.TeamMemberType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NomadSaveTest {

    @Test
    void nomadShouldSavePlayerFromDamageOnce() {
        Player player = new Player("Test", 3, 0, 0, 0);
        player.recruit(TeamMemberType.KLAJOKLIS);

        Sector sector = new Sector(0, 0);

        TrapEvent event = new TrapEvent() {
            @Override
            public void handle(Player p, Sector s, int pr, int pc) {
                boolean saved = p.useNomadToSave();
                if (!saved) {
                    p.takeDamage(1);
                }
            }
        };

        event.handle(player, sector, 0, 0);

        assertEquals(3, player.getLives(), "Gyvybės neturėjo sumažėti, nes Klajoklis išgelbėjo");
    }
}
