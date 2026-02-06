package dev.msky.pixelui.media;

import java.util.Objects;

public abstract sealed class CMedia permits CMediaFont, CMediaSound, CMediaSprite, CMediaTexture {

    private transient int cachedHashCode = 0;
    public String file;
    public int fileID;

    public CMedia() {
    }

    CMedia(String file, int fileID) {
        this.file = file;
        this.fileID = fileID;
    }

    protected void copyFields(CMedia copyFrom) {
        this.file = copyFrom.file;
        this.fileID = copyFrom.fileID;
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

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        CMedia cMedia = (CMedia) object;
        return fileID == cMedia.fileID && Objects.equals(file, cMedia.file);
    }

    @Override
    public int hashCode() {
        if (cachedHashCode == 0)
            cachedHashCode = Objects.hash(file, fileID);
        return cachedHashCode;
    }
}