package net.mslivo.pixelui.media;


public sealed abstract class CMediaSprite extends CMedia permits CMediaAnimation, CMediaArray, CMediaImage {

    public CMediaSprite() {
        super();
    }

    public CMediaSprite(String filename) {
        super(filename);
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }

}
