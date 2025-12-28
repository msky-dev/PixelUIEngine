package net.mslivo.pixelui.media;

import net.mslivo.pixelui.utils.helper.Copyable;

public final class CMediaImage extends CMediaSprite implements Copyable<CMediaImage> {

    public CMediaImage() {
        super();
    }

    public CMediaImage(String filename) {
        super(filename);
    }

    public CMediaImage copy() {
        CMediaImage copy = new CMediaImage();
        copy.copyFields(this);
        return copy;
    }

}
