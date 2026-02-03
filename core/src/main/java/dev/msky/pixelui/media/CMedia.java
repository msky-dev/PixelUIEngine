package dev.msky.pixelui.media;

public abstract sealed class CMedia permits CMediaFont, CMediaSound, CMediaSprite, CMediaTexture {

    public String file;
    public int mediaManagerIndex;

    public CMedia() {
        this.file = "";
        this.mediaManagerIndex = -1;
    }

    CMedia(String file) {
        this.file = file;
    }

    protected void copyFields(CMedia copyFrom) {
        this.file = copyFrom.file;
        this.mediaManagerIndex = copyFrom.mediaManagerIndex;
    }

    public CMedia copy() {
        CMedia copy = switch (this) {
            case CMediaFont cMediaFont -> cMediaFont.copy();
            case CMediaSound cMediaSound -> cMediaSound.copy();
            case CMediaSprite cMediaSprite -> cMediaSprite.copy();
            case CMediaTexture cMediaTexture -> cMediaTexture.copy();
        };
        return copy;
    }

}