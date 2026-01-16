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

    public CMediaTexture(String file,int fileID) {
        this(file,fileID, Pixmap.Format.RGBA8888,Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file,int fileID, Pixmap.Format format) {
        this(file,fileID,format,Texture.TextureFilter.Nearest, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file,int fileID, Pixmap.Format format,Texture.TextureFilter filter) {
        this(file,fileID,format, filter, Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file,int fileID, Pixmap.Format format,Texture.TextureFilter filter, Texture.TextureWrap uWrap) {
        this(file,fileID, format, filter, uWrap, Texture.TextureWrap.ClampToEdge);
    }

    public CMediaTexture(String file, int fileID, Pixmap.Format format, Texture.TextureFilter filter, Texture.TextureWrap uWrap, Texture.TextureWrap vWrap) {
        super(file,fileID);
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

}
