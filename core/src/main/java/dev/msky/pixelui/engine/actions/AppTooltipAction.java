package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.Tooltip;

public interface AppTooltipAction {

    default Tooltip onShowTooltip(){
        return null;
    }

}
