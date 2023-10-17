package com.example.far_faraway;

public class Player {
    String name;
    int level;

    public Player() {}

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setParam(String _name, int _level) {
        name = _name;
        level = _level;
    }
}
