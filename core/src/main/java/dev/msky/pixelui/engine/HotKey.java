package dev.msky.pixelui.engine;

import dev.msky.pixelui.engine.actions.HotKeyAction;

public class HotKey {
    public int[] keyCodes;
    public boolean pressed;
    public HotKeyAction hotKeyAction;
    public String name;
    public Object data;

    HotKey() {
    }
}
