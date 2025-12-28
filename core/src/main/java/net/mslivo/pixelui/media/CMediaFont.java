package net.mslivo.pixelui.media;

import net.mslivo.pixelui.utils.helper.Copyable;

import java.util.Arrays;
import java.util.Objects;

public final class CMediaFont extends CMedia implements Copyable<CMediaFont> {
    public boolean markupEnabled;
    public CMediaFontOutline outline;
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

    public CMediaFont(String filename, boolean markupEnabled, CMediaFontSymbol[] symbols, CMediaFontOutline outline) {
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
            this.outline = new CMediaFontOutline(outline.color, outline.directions, outline.outlineSymbols, outline.outlineOnly);
        } else {
            this.outline = null;
        }
    }

    @Override
    public CMediaFont copy() {
        CMediaFont copy = new CMediaFont();
        copy.copyFields(this);
        copy.markupEnabled = this.markupEnabled;
        copy.outline = this.outline.copy();
        copy.symbols = new CMediaFontSymbol[this.symbols.length];
        for (int i = 0; i < this.symbols.length; i++)
            copy.symbols[i] = this.symbols[i].copy();
        return copy;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        CMediaFont that = (CMediaFont) object;
        return markupEnabled == that.markupEnabled && Objects.equals(outline, that.outline) && Objects.deepEquals(symbols, that.symbols);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), markupEnabled, outline, Arrays.hashCode(symbols));
    }
}
