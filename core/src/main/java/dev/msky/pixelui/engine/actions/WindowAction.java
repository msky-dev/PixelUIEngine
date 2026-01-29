package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;
import dev.msky.pixelui.engine.actions.common.Displayable;
import dev.msky.pixelui.engine.actions.common.Icon;

public interface WindowAction extends CommonActions, Icon, Displayable {

    default void onMove(int x, int y) {
    }

    default void onFold() {
    }

    default void onUnfold() {
    }

    default void onMessageReceived(int type, Object... parameters) {
    }

}
