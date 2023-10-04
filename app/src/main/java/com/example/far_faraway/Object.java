package com.example.far_faraway;

import static com.example.far_faraway.Scene.getResId;
import static com.example.far_faraway.Scene.inventory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.core.content.res.ResourcesCompat;

public class Object extends androidx.appcompat.widget.AppCompatButton {
    String name;
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
        icon = ResourcesCompat.getDrawable(getResources(), getResId(_icon, R.drawable.class), null);
        this.setBackground(icon);
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
        icon = ResourcesCompat.getDrawable(getResources(), getResId(_newIcon, R.drawable.class), null);
        this.setBackground( icon );
    }

    public String getName() {
        return name;
    }

}
