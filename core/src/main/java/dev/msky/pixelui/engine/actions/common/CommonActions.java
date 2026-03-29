package dev.msky.pixelui.engine.actions.common;

import com.badlogic.gdx.graphics.Color;
import dev.msky.pixelui.engine.Tooltip;
import dev.msky.pixelui.media.CMediaSprite;


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

    default CMediaSprite overlaySprite(){
        return null;
    }

    default Color overlaySpriteColor(){
        return Color.GRAY;
    }

    default int overlaySpriteIndex(){
        return 0;
    };

}
