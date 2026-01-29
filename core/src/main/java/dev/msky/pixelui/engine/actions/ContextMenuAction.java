package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;
import dev.msky.pixelui.engine.actions.common.Displayable;
import dev.msky.pixelui.engine.ContextMenuItem;

public interface ContextMenuAction extends CommonActions, Displayable {

    default void onItemSelected(ContextMenuItem selectedItem) {
    }

}
