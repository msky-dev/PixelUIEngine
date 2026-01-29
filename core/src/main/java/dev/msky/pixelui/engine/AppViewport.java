package dev.msky.pixelui.engine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.msky.pixelui.rendering.NestedFrameBuffer;
import dev.msky.pixelui.engine.actions.AppViewPortAction;

public final class AppViewport extends Component {
    public OrthographicCamera camera;
    public NestedFrameBuffer frameBuffer;
    public TextureRegion textureRegion;
    public AppViewPortAction appViewPortAction;
    public long updateTimer;
    public int updateTime;

    AppViewport() {
    }
}
