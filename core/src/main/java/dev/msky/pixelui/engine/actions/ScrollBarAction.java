package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;

public interface ScrollBarAction extends CommonActions {

    default void onScrolled(float scrolled) {
    }

    default void onPress(float scrolled) {
    }

    default void onRelease(float scrolled) {
    }

}
