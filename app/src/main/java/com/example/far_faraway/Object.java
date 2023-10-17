package com.example.far_faraway;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.inventory;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.core.content.res.ResourcesCompat;

public class Object extends androidx.appcompat.widget.AppCompatButton {
    String name;
    String iconName;
    Drawable icon;

    public Object(Context _context) {
        super(_context);
    }

    public Object(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Object(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParam(String _name, String _icon) {
        name = _name;

        setIcon(_icon);
    }

    public boolean setToInventory() {

        for (int i = 0; i < inventory.length; i++)
            if (inventory[i] == null) {
                inventory[i] = this;
                return true;
            }

        return false;
    }

    public void setIcon(String _newIcon) {
        iconName = _newIcon;

        if (_newIcon.equals("none"))
            this.setBackgroundColor(Color.TRANSPARENT);
        else {
            icon = ResourcesCompat.getDrawable(getResources(), getResId(iconName, R.drawable.class), null);
            this.setBackground( ResourcesCompat.getDrawable(getResources(), getResId(_newIcon, R.drawable.class), null) );
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getIcon() {
        return iconName;
    }
}
