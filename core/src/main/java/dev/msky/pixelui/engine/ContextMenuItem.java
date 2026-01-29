package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.Color;
import dev.msky.pixelui.engine.actions.ContextMenuItemAction;

public class ContextMenuItem {
    public String text;
    public Color fontColor;
    public ContextMenuItemAction contextMenuItemAction;
    public ContextMenu addedToContextMenu;
    public String name;
    public Object data;

    ContextMenuItem() {
    }
}
