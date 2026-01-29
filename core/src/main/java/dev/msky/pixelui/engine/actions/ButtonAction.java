package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;
import dev.msky.pixelui.engine.actions.common.Icon;

public interface ButtonAction extends CommonActions, Icon {

    default void onPress() {
    }

    default void onRelease() {
    }

    default boolean onToggle(boolean value) {
        return true;
    }

}
