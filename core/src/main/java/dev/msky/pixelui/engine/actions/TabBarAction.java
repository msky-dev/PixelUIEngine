package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.CommonActions;
import dev.msky.pixelui.engine.Tab;

public interface TabBarAction extends CommonActions {

    default void onChangeTab(int index, Tab tab){}

}
