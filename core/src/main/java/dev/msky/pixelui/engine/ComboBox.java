package dev.msky.pixelui.engine;

import com.badlogic.gdx.utils.Array;
import dev.msky.pixelui.engine.actions.ComboBoxAction;

public final class ComboBox extends Component {
    public Array<ComboBoxItem> items;
    public ComboBoxAction comboBoxAction;
    public ComboBoxItem selectedItem;

    ComboBox() {
    }
}
