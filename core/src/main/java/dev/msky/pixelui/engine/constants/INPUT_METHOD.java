package dev.msky.pixelui.engine.constants;

public enum INPUT_METHOD {
    MOUSE_AND_KEYBOARD("Mouse/Keyboard"),
    GAMEPAD("Gamepad"),
    NONE("None");

    public final String text;

    INPUT_METHOD(String text) {
        this.text = text;
    }
}
