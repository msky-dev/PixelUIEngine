package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.Tooltip;
import dev.msky.pixelui.engine.actions.common.CellColor;
import dev.msky.pixelui.engine.actions.common.Icon;

public interface ComboBoxItemAction extends Icon, CellColor {
    default void onSelect() {
    }

    default Tooltip onShowTooltip(){
        return null;
    }

}
