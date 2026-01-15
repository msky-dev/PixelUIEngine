package net.mslivo.pixelui.engine.actions;

import net.mslivo.pixelui.engine.Tooltip;
import net.mslivo.pixelui.engine.actions.common.CellColor;
import net.mslivo.pixelui.engine.actions.common.Icon;

public interface ComboBoxItemAction extends Icon, CellColor {
    default void onSelect() {
    }

    default Tooltip onShowTooltip(){
        return null;
    }

}
