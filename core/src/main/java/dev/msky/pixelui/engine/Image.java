package dev.msky.pixelui.engine;

import dev.msky.pixelui.media.CMediaSprite;
import dev.msky.pixelui.engine.actions.ImageAction;

public final class Image extends Component {
    public CMediaSprite image;
    public int arrayIndex;
    public ImageAction imageAction;
    public boolean flipX, flipY,stretchToSize;

    Image() {
    }
}
