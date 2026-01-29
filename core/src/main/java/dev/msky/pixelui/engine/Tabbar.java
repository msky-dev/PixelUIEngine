package dev.msky.pixelui.engine;

import com.badlogic.gdx.utils.Array;
import dev.msky.pixelui.engine.actions.TabBarAction;

public final class Tabbar extends Component {
    public Array<Tab> tabs;
    public int selectedTab;
    public TabBarAction tabBarAction;
    public boolean border;
    public int borderHeight;
    public int tabOffset;
    public boolean bigIconMode;

    Tabbar() {
    }
}
