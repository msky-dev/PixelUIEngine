package dev.msky.pixelui.media;

import java.util.Objects;

public sealed abstract class CMediaFontSymbol permits CMediaFontSymbolArray, CMediaFontSymbolSingle {
    private transient int cachedHashCode = 0;
    public String file;
    public int fileID;
    public int y_offset;
    public int x_advance;

    public CMediaFontSymbol() {
        super();
        this.y_offset = 0;
        this.x_advance = 0;
    }

    public CMediaFontSymbol(String file, int fileID, int y_offset, int x_advance) {
        this.file = file;
        this.fileID = fileID;
        this.y_offset = y_offset;
        this.x_advance = x_advance;
    }

    public CMediaFontSymbol copy() {
        CMediaFontSymbol copy = switch (this) {
            case CMediaFontSymbolArray cMediaFontArraySymbol -> cMediaFontArraySymbol.copy();
            case CMediaFontSymbolSingle cMediaFontSingleSymbol -> cMediaFontSingleSymbol.copy();
        };
        return copy;
    }

    protected void copyFields(CMediaFontSymbol copyFrom) {
        this.file = copyFrom.file;
        this.fileID = copyFrom.fileID;
        this.y_offset = copyFrom.y_offset;
        this.x_advance = copyFrom.x_advance;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        CMediaFontSymbol cMedia = (CMediaFontSymbol) object;
        return fileID == cMedia.fileID && Objects.equals(file, cMedia.file);
    }

    @Override
    public int hashCode() {
        if (cachedHashCode == 0)
            cachedHashCode = Objects.hash(file, fileID);
        return cachedHashCode;
    }

}
