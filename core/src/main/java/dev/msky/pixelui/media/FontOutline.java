package dev.msky.pixelui.media;

import com.badlogic.gdx.graphics.Color;

public record FontOutline(Color color, OUTLINE directions, boolean outlineSymbols, boolean outlineOnly) {
    public FontOutline(FontOutline fontOutline) {
        this(fontOutline.color(),fontOutline.directions(),fontOutline.outlineSymbols(),fontOutline.outlineOnly());
    }
}