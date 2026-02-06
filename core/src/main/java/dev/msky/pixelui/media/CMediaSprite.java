package dev.msky.pixelui.media;


public sealed abstract class CMediaSprite extends CMedia permits CMediaAnimation, CMediaArray, CMediaImage {

    public CMediaSprite() {
        super();
    }

    public CMediaSprite(String filename,int identifier) {
        super(filename,identifier);
    }

    public CMediaSprite copy() {
        CMediaSprite copy = switch (this) {
            case CMediaAnimation cMediaAnimation -> cMediaAnimation.copy();
            case CMediaArray cMediaArray -> cMediaArray.copy();
            case CMediaImage cMediaImage -> cMediaImage.copy();
        };
        return copy;
    }

    protected void copyFields(CMediaSprite copyFrom) {
        super.copyFields(copyFrom);
    }

}
