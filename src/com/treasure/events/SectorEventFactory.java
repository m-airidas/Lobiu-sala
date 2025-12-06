package com.treasure.events;

import com.treasure.EventType;

public class SectorEventFactory  {

    public static SectorEvent create(EventType type){
        return switch (type) {
            case OBSTACLE -> new ObstacleEvent();
            case TRAP -> new TrapEvent();
            case ENEMY -> new GuardEvent();
            case MAIN_TREASURE -> new MainTreasureEvent();
            case SECONDARY_TREASURE -> new SecondaryTreasureEvent();
            case TEAM_MEMBER  -> new MemberEvent();
            case NONE -> new NoEvent();
        };
    }
}
