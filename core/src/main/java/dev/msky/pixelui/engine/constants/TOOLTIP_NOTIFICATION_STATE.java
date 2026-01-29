package dev.msky.pixelui.engine.constants;

public enum TOOLTIP_NOTIFICATION_STATE {
    INIT("Init"),
    DISPLAY("Display"),
    FADE("Fade"),
    FINISHED("Finished");

    public final String text;

    TOOLTIP_NOTIFICATION_STATE(String text) {
        this.text = text;
    }
}
