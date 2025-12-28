package net.mslivo.pixelui.media;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import net.mslivo.pixelui.utils.helper.Copyable;

import java.util.Objects;

// Stored Off the atlas
public final class CMediaTexture extends CMedia implements Copyable<CMediaTexture> {

    public Pixmap.Format format;
    public Texture.TextureFilter filter;
    public Texture.TextureWrap uWrap, vWrap;

    public CMediaTexture() {
        super();
        this.format = Pixmap.Format.RGBA8888;
        this.filter = Texture.TextureFilter.Nearest;
        this.uWrap = Texture.TextureWrap.ClampToEdge;
        this.vWrap = Texture.TextureWrap.ClampToEdge;
    }

    public CMediaTexture(String file) {
        this(file, Pixmap.Format.RGBA8888,Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file, Pixmap.Format format) {
        this(file,format,Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file, Pixmap.Format format,Texture.TextureFilter filter) {
        this(file,format, filter, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file, Pixmap.Format format,Texture.TextureFilter filter, Texture.TextureWrap uWrap) {
        this(file, format, filter, uWrap, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file, Pixmap.Format format, Texture.TextureFilter filter, Texture.TextureWrap uWrap, Texture.TextureWrap vWrap) {
        super(file);
        this.format = format;
        this.filter = filter;
        this.uWrap = uWrap;
        this.vWrap = vWrap;
    }

    @Override
    public CMediaTexture copy() {
        CMediaTexture copy = new CMediaTexture();
        copy.copyFields(this);
        copy.format = this.format;
        copy.filter = this.filter;
        copy.uWrap = this.uWrap;
        copy.vWrap = this.vWrap;
        return copy;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        CMediaTexture that = (CMediaTexture) object;
        return format == that.format && filter == that.filter && uWrap == that.uWrap && vWrap == that.vWrap;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), format, filter, uWrap, vWrap);
    }
}
