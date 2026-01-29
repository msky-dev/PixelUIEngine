package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.Color;
import dev.msky.pixelui.engine.actions.ProgressBarAction;

public final class Progressbar extends Component {
    public float progress;
    public boolean progressText;
    public boolean progressText2Decimal;
    public Color fontColor;
    public ProgressBarAction progressBarAction;

    Progressbar() {
    }
}
