package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;

public interface AppViewPortAction extends CommonActions {

    default void onPress(int x, int y) {
    }

    default void onRelease() {
    }


}
