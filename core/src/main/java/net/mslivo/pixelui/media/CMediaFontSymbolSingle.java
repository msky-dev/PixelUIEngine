package net.mslivo.pixelui.media;

import net.mslivo.pixelui.utils.helper.Copyable;

public final class CMediaFontSymbolSingle extends CMediaFontSymbol implements Copyable<CMediaFontSymbolSingle> {
    public int id;

    public CMediaFontSymbolSingle() {
        super();
        this.id = 0;
    }

    public CMediaFontSymbolSingle(int id, String file,int fileID) {
        this(id, file,fileID, 0,0);
    }

    public CMediaFontSymbolSingle(int id, String file,int fileID, int y_offset) {
        this(id, file, fileID,y_offset,0);
    }

    public CMediaFontSymbolSingle(int id, String file,int fileID, int y_offset, int x_advance) {
        super(file,fileID, y_offset, x_advance);
        this.id = id;
    }

    @Override
    public CMediaFontSymbolSingle copy(){
        CMediaFontSymbolSingle copy = new CMediaFontSymbolSingle();
        copy.copyFields(this);
        copy.id = this.id;
        return copy;
    }

}
