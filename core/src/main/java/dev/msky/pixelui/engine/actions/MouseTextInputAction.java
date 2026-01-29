package dev.msky.pixelui.engine.actions;

import dev.msky.pixelui.engine.actions.common.Displayable;

public interface MouseTextInputAction extends Displayable {

    /*Return = close yes/no */

    default boolean onConfirm(){
        return true;
    }
    default void onEnterCharacter(char c){return;}
    default void onChangeCase(boolean upperCase){return;}
    default void onDelete(){return;}

}
