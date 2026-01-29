package dev.msky.pixelui.engine;

import com.badlogic.gdx.utils.Array;
import dev.msky.pixelui.engine.actions.GridAction;

public final class Grid<T> extends Component {
    public T[][] items;
    public GridAction<T> gridAction;
    public T selectedItem;
    public boolean multiSelect;
    public Array<T> selectedItems;
    public boolean dragEnabled;
    public boolean dragOutEnabled;
    public boolean dragInEnabled;
    public boolean bigMode;

    Grid() {
    }
}
