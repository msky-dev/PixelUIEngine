package dev.msky.pixelui.media;

import dev.msky.pixelui.utils.helper.Copyable;

public final class CMediaArray extends CMediaSprite implements Copyable<CMediaArray> {
    public int frameWidth;
    public int frameHeight;
    public int frameOffset;
    public int frameLength;

    public CMediaArray() {
        super();
        this.frameWidth = 0;
        this.frameHeight = 0;
        this.frameOffset = 0;
        this.frameLength = Integer.MAX_VALUE;
    }

    public CMediaArray(String file, int fileID, int frameWidth, int frameHeight) {
        this(file,fileID, frameWidth, frameHeight, 0, Integer.MAX_VALUE);
    }

    public CMediaArray(String file,int fileID, int frameWidth, int frameHeight, int frameOffset, int frameLength) {
        super(file,fileID);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameOffset = frameOffset;
        this.frameLength = frameLength;
    }

    @Override
    public CMediaArray copy() {
        CMediaArray copy = new CMediaArray();
        copy.copyFields(this);
        copy.frameWidth = this.frameWidth;
        copy.frameHeight = this.frameHeight;
        copy.frameOffset = this.frameOffset;
        copy.frameLength = this.frameLength;
        return copy;
    }

}
