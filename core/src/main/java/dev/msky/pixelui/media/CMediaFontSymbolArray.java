package dev.msky.pixelui.media;

import dev.msky.pixelui.utils.helper.Copyable;

public final class CMediaFontSymbolArray extends CMediaFontSymbol implements Copyable<CMediaFontSymbolArray> {
    public int[] ids;
    public int regionWidth;
    public int regionHeight;
    public int frameOffset;
    public int frameLength;

    public CMediaFontSymbolArray() {
        super();
        this.ids = new int[]{};
        this.regionWidth = 0;
        this.regionHeight = 0;
        this.frameOffset = 0;
        this.frameLength = 0;
    }

    public CMediaFontSymbolArray(int[] ids, String file, int y_offset, int x_advance, int regionWidth, int regionHeight) {
        this(ids, file, y_offset, x_advance,regionWidth,regionHeight, 0, Integer.MAX_VALUE);
    }

    public CMediaFontSymbolArray(int[] ids, String file, int y_offset, int x_advance, int regionWidth, int regionHeight, int frameOffset, int frameLength) {
        super(file, y_offset, x_advance);
        if(ids != null){
            this.ids = new int[ids.length];
            System.arraycopy(ids,0,this.ids,0,ids.length);
        }else{
            this.ids= new int[]{};
        }
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        this.frameOffset = frameOffset;
        this.frameLength = frameLength;
    }

    public CMediaFontSymbolArray copy(){
        CMediaFontSymbolArray copy = new CMediaFontSymbolArray();
        copy.copyFields(this);
        copy.ids = new int[this.ids.length];
        for(int i=0;i<this.ids.length;i++)
            copy.ids[i] = this.ids[i];
        copy.regionWidth = this.regionWidth;
        copy.regionHeight = this.regionHeight;
        copy.frameOffset = this.frameOffset;
        copy.frameLength = this.frameLength;
        return copy;
    }

}
