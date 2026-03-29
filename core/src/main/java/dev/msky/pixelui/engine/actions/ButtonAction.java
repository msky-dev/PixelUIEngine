package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;
import dev.msky.pixelui.engine.actions.common.Icon;
import dev.msky.pixelui.media.CMediaSound;
import dev.msky.pixelui.media.CMediaSprite;

public interface ButtonAction extends CommonActions, Icon {

    default void onPress() {
    }

    default void onRelease() {
    }

    default boolean onToggle(boolean value) {
        return true;
    }

}
