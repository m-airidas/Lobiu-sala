package com.treasure;

public class Sector {
    private final int row, col;
    private boolean trap = false;
    private boolean enemy = false;
    private boolean obstacle = false;
    private boolean visited = false;
    private boolean start = false;
    private boolean mainTreasure = false;
    private boolean secondaryTreasure = false;
    private boolean teamMember = false;
    private String description = null;

    public Sector(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        if (description != null) return description;
        if (start) return "Pradžia.";
        if (mainTreasure) return "Čia kažkas užkasta... Gal lobis?";
        if (secondaryTreasure) return "Kažkas blizga smėlyje.";
        if (trap) return "Spąstai!";
        if (enemy) return "Budi sargybinis!";
        if (obstacle) return "Kliūtis!";
        if (teamMember) return "Komandos narys!";
        return "Tuščias sektorius.";
    }

    public EventType triggerEvent() {
        if (mainTreasure) { return EventType.MAIN_TREASURE; }
        if (secondaryTreasure) { return EventType.SECONDARY_TREASURE; }
        if (trap) { return EventType.TRAP; }
        if (enemy) { return EventType.ENEMY; }
        if (obstacle) { return EventType.OBSTACLE; }
        if (teamMember) { return EventType.TEAM_MEMBER; }
        return EventType.NONE;
    }

    public boolean isVisited() { return visited; }
    public void setVisited(boolean v) { visited = v; }
    public boolean isStart() { return start; }
    public void setStart(boolean s) { start = s; }
    public boolean hasMainTreasure() { return mainTreasure; }
    public void setMainTreasure(boolean t) { mainTreasure = t; }
    public boolean hasSecondaryTreasure() { return secondaryTreasure; }
    public void setSecondaryTreasure(boolean t) { secondaryTreasure = t; }
    public boolean isTrap() { return trap; }
    public void setTrap(boolean t) { trap = t; }
    public boolean isEnemy() { return enemy; }
    public void setEnemy(boolean e) { enemy = e; }
    public boolean isObstacle() { return obstacle; }
    public void setObstacle(boolean o) { obstacle = o; }
    public boolean hasTeamMember() { return teamMember; }
    public void setTeamMember(boolean b) { teamMember = b; }
    public void clearTeamMember() { teamMember = false; }

    public void clearTrap() {trap = false;}
    public void clearObstacle() {obstacle = false;}
    public void clearEnemy() { enemy = false; }
    public void clearSecondary() { secondaryTreasure = false; }
}
