package com.example.far_faraway;

import static com.example.far_faraway.Scene.getResId;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.core.content.res.ResourcesCompat;

public class Holder extends androidx.appcompat.widget.AppCompatButton {
    String name;
    Object keep;
    String need;

    public Holder(Context context) {
        super(context);
    }

    public Holder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Holder(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParam(String _name, String _need, String _icon) {
        name = _name;
        need = _need.trim();

        if (_icon.equals("none"))
            this.setBackgroundColor(Color.TRANSPARENT);
        else
            this.setBackground( ResourcesCompat.getDrawable(getResources(), getResId(_icon, R.drawable.class), null) );
    }

    public boolean setItem(Object obj) {

        if ( need.equals(obj.name) ) {
            keep = obj;
            return true;
        }

        return false;
    }

    public void setIcon(String _newIcon) {

        if (_newIcon.equals("none"))
            this.setBackgroundColor(Color.TRANSPARENT);
        else
            this.setBackground( ResourcesCompat.getDrawable(getResources(), getResId(_newIcon, R.drawable.class), null) );
    }
}
