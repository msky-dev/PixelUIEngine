package net.mslivo.pixelui.media;

import net.mslivo.pixelui.utils.helper.Copyable;

public final class CMediaImage extends CMediaSprite implements Copyable<CMediaImage> {

    public CMediaImage() {
        super();
    }

    public CMediaImage(String filename, int fileID) {
        super(filename,fileID);
    }

    public CMediaImage copy() {
        CMediaImage copy = new CMediaImage();
        copy.copyFields(this);
        return copy;
    }

}
