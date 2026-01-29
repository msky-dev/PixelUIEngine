package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;

public interface CheckboxAction extends CommonActions {

    default void onCheck(boolean checked) {
    }

}
