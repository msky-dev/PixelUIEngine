package dev.msky.pixelui.engine;

import dev.msky.pixelui.media.CMediaFont;
import dev.msky.pixelui.rendering.SpriteRenderer;

public abstract class TextRenderHook {

    public CMediaFont replaceFont(Object uiObject, CMediaFont configFont){
        return configFont;
    };

    public String replaceText(Object uiObject, String text){
        return text;
    };

    public void render(Object uiObject, SpriteRenderer spriteRenderer, CMediaFont font, int x, int y, String text, int textOffset, int textLength, int maxWidth){
        spriteRenderer.drawCMediaFont(font, x, y, text, textOffset, textLength, false, false, maxWidth);
    };
}
