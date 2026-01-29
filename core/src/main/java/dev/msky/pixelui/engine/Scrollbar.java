package dev.msky.pixelui.engine;

import dev.msky.pixelui.engine.actions.ScrollBarAction;

public abstract sealed class Scrollbar extends Component permits ScrollbarHorizontal, ScrollbarVertical {
    public float scrolled;
    public boolean buttonPressed;
    public ScrollBarAction scrollBarAction;

    Scrollbar() {
    }
}
