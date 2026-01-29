package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CellColor;
import dev.msky.pixelui.engine.actions.common.Icon;

public interface ContextMenuItemAction extends Icon, CellColor {

    default void onSelect() {
    }

}