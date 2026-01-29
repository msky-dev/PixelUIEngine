package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.ItemCellColor;
import dev.msky.pixelui.engine.actions.common.CommonActions;
import dev.msky.pixelui.engine.Grid;
import dev.msky.pixelui.engine.List;
import dev.msky.pixelui.engine.Tooltip;
import dev.msky.pixelui.engine.actions.common.ItemIcons;

public interface ListAction<T> extends CommonActions, ItemIcons<T>, ItemCellColor<T> {

    default Tooltip onShowToolTip(T listItem) {
        return null;
    }

    default String text(T listItem) {
        return listItem.toString();
    }

    default boolean onItemSelected(T listItem) {
        return true;
    }

    default void onScrolled(float scrolled) {
    }

    /* Drag */

    default void onDragFromList(List fromList, int fromIndex, int toIndex) {
    }

    default void onDragFromGrid(Grid fromGrid, int from_x, int from_y, int toIndex) {
    }

    default boolean canDragFromList(List list) {
        return false;
    }

    default boolean canDragFromGrid(Grid fromGrid) {
        return false;
    }

    default void onDragIntoApp(T listItem, int mouseX, int mouseY) {
    }

    default boolean canDragIntoApp() {
        return false;
    }

}
