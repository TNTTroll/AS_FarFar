package com.example.far_faraway;

public class ObjectInfo {
    String name;
    String icon;
    boolean used;

    public ObjectInfo(String _name, String _icon) {
        name = _name;
        icon = _icon;
        used = false;
    }

    public String getName() { return name; }

    public String getIcon() { return icon; }

    public boolean getUsed() { return used; }

    public void setUsed(boolean _used) { used = _used; }
}
