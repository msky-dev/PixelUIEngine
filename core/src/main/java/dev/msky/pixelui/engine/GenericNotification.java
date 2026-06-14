package dev.msky.pixelui.engine;

public abstract sealed class GenericNotification permits Notification, TooltipNotification {
    public long timer;
    public int displayTimeMS;
    public String name;
    public Object data;
    public boolean addedToScreen;

    GenericNotification() {
    }
}
