package dev.msky.pixelui.engine.actions.common;

import dev.msky.pixelui.engine.Tooltip;

public interface CommonActions {

    default void onMousePress(int button) {
    }

    default void onMouseRelease(int button) {
    }

    default void onMouseDoubleClick(int button) {
    }

    default void onMouseScroll(float scrolled) {
    }

    default Tooltip onShowTooltip(){
        return null;
    }

}
