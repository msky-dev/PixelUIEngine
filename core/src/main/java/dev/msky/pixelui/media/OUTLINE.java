package dev.msky.pixelui.media;

public enum OUTLINE {
    NONE(false, false, false, false),

    UP(true, false, false, false),
    DOWN(false, true, false, false),
    LEFT(false, false, true, false),
    RIGHT(false, false, false, true),

    UP_DOWN(true, true, false, false),
    UP_LEFT(true, false, true, false),
    UP_RIGHT(true, false, false, true),
    DOWN_LEFT(false, true, true, false),
    DOWN_RIGHT(false, true, false, true),
    LEFT_RIGHT(false, false, true, true),

    UP_DOWN_LEFT(true, true, true, false),
    UP_DOWN_RIGHT(true, true, false, true),
    UP_LEFT_RIGHT(true, false, true, true),
    DOWN_LEFT_RIGHT(false, true, true, true),

    ALL(true, true, true, true);

    public final boolean up;
    public final boolean down;
    public final boolean left;
    public final boolean right;

    OUTLINE(boolean up, boolean down, boolean left, boolean right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }
}