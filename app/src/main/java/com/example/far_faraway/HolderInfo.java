package com.example.far_faraway;

public class HolderInfo {
    String name;
    String need;
    String icon;
    boolean used = false;

    public HolderInfo(String _name, String _need, String _icon) {
        name = _name;
        need = _need;
        icon = _icon;
    }

    public String getName() { return name; }

    public String getNeed() { return need; }

    public String getIcon() { return icon; }
}
