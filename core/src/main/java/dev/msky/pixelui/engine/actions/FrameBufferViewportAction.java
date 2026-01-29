package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;

public interface FrameBufferViewportAction extends CommonActions {

    default void onPress(int x, int y) {
    }

    default void onRelease() {
    }

}
