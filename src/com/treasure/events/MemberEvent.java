package com.treasure.events;

import com.treasure.Player;
import com.treasure.Sector;

public class MemberEvent extends BasicSectorEvent {

    @Override
    public void handle(Player player, Sector sector, int previousRow, int previousCol) {
        System.out.println("🤝 Radai komandos narį!");
        System.out.println("Įvesk RECRUIT, kad prisijungtų prie tavęs.");
    }
}
