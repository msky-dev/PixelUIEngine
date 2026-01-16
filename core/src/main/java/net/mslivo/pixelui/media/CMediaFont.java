package net.mslivo.pixelui.media;

import com.badlogic.gdx.graphics.Color;
import net.mslivo.pixelui.utils.helper.Copyable;

public final class CMediaFont extends CMedia implements Copyable<CMediaFont> {
    public boolean markupEnabled;
    public FontOutline outline;
    public CMediaFontSymbol[] symbols;

    public CMediaFont() {
        super();
        this.markupEnabled = true;
        this.outline = null;
        this.symbols = new CMediaFontSymbol[]{};
    }


    public CMediaFont(String file) {
        this(file, true, null, null);
    }

    public CMediaFont(String file, boolean markupEnabled) {
        this(file, markupEnabled, null, null);
    }

    public CMediaFont(String file, boolean markupEnabled, CMediaFontSymbol[] symbols) {
        this(file, markupEnabled, symbols, null);
    }

    public CMediaFont(String filename, boolean markupEnabled, CMediaFontSymbol[] symbols, FontOutline outline) {
        super(filename);
        this.markupEnabled = markupEnabled;
        if (symbols != null) {
            this.symbols = new CMediaFontSymbol[symbols.length];
            for (int i = 0; i < symbols.length; i++)
                this.symbols[i] = symbols[i].copy();
        } else {
            this.symbols = new CMediaFontSymbol[0];
        }

        if (outline != null) {
            this.outline = new FontOutline(outline.color, outline.directions, outline.outlineSymbols, outline.outlineOnly);
        } else {
            this.outline = null;
        }
    }

    @Override
    public CMediaFont copy() {
        CMediaFont copy = new CMediaFont();
        copy.copyFields(this);
        copy.markupEnabled = this.markupEnabled;
        copy.outline = new FontOutline(this.outline);
        copy.symbols = new CMediaFontSymbol[this.symbols.length];
        for (int i = 0; i < this.symbols.length; i++)
            copy.symbols[i] = this.symbols[i].copy();
        return copy;
    }

    public record FontOutline(Color color, int directions,boolean outlineSymbols, boolean outlineOnly) {
        public FontOutline(FontOutline fontOutline) {
            this(fontOutline.color(),fontOutline.directions(),fontOutline.outlineSymbols(),fontOutline.outlineOnly());
        }
    }

}
