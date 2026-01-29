package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.Color;
import dev.msky.pixelui.engine.actions.ComboBoxItemAction;

public class ComboBoxItem {
    public String text;
    public Color fontColor;
    public ComboBoxItemAction comboBoxItemAction;
    public ComboBox addedToComboBox;
    public String name;
    public Object data;

    ComboBoxItem() {
    }
}
