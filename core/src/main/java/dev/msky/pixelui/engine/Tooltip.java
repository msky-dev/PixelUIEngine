package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import dev.msky.pixelui.engine.constants.DIRECTION;
import dev.msky.pixelui.engine.actions.common.UpdateActionSupport;
import dev.msky.pixelui.engine.actions.ToolTipAction;

public class Tooltip extends UpdateActionSupport {
    public ToolTipAction toolTipAction;
    public Array<TooltipSegment> segments;
    public int minWidth;
    public Color color_border;
    public Color color_line;
    public int lineLength;
    public DIRECTION direction;
    public String name;
    public Object data;

    Tooltip() {
    }
}
