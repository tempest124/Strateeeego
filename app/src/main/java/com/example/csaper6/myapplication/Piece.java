package com.example.csaper6.myapplication;

import android.graphics.drawable.Drawable;

public class Piece {
    private int power;
       private boolean team;
    private boolean isHidden;
    private Space space;
    private Drawable image;

    public Piece(int power, boolean team, Space space, boolean isHidden, Drawable image) {
        this.power=power;
                this.team=team;
        this.space=space;
        this.isHidden=isHidden;
        this.image=image;

    }
    public int getPower() {
        return power;
    }
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
    public boolean isTeam() {
        return team;
    }
    public Space getSpace() {
        return space;
    }
    public void setSpace(Space space) {
        this.space = space;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
