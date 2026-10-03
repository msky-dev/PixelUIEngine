package dev.msky.pixelui.engine.constants;

public enum MOUSE_CONTROL_MODE {
    HARDWARE_MOUSE("Mouse"),
    GAMEPAD("Gamepad"),
    DISABLED("Disabled");

    public final String text;
    MOUSE_CONTROL_MODE(String text){
        this.text = text;
    }
}
