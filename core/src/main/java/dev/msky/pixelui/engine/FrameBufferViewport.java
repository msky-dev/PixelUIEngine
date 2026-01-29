package dev.msky.pixelui.engine;

import dev.msky.pixelui.rendering.NestedFrameBuffer;
import dev.msky.pixelui.engine.actions.FrameBufferViewportAction;

public final class FrameBufferViewport extends Component {
    public NestedFrameBuffer frameBuffer;
    public FrameBufferViewportAction frameBufferViewportAction;
    public boolean flipX, flipY,stretchToSize;

    FrameBufferViewport() {
    }
}
