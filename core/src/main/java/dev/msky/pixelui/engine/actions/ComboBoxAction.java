package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;
import dev.msky.pixelui.engine.actions.common.Displayable;
import dev.msky.pixelui.engine.ComboBoxItem;

public interface ComboBoxAction extends CommonActions, Displayable {

    default void onItemSelected(ComboBoxItem selectedItem) {
    }

}
