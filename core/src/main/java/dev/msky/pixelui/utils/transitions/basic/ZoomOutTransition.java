package dev.msky.pixelui.utils.transitions.basic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import dev.msky.pixelui.utils.transitions.TRANSITION_RENDER_ORDER;
import dev.msky.pixelui.utils.transitions.TRANSITION_SPEED;
import dev.msky.pixelui.utils.transitions.Transition;
import dev.msky.pixelui.rendering.SpriteRenderer;

public class ZoomOutTransition extends Transition {
    private float zoom, zoomAcc;
    private int screenWidth;
    private int screenHeight;

    public ZoomOutTransition() {
        super();
    }

    public ZoomOutTransition(TRANSITION_SPEED transitionSpeed) {
        super(transitionSpeed);
    }

    @Override
    public TRANSITION_RENDER_ORDER getRenderOrder() {
        return TRANSITION_RENDER_ORDER.TO_FIRST;
    }

    @Override
    public void init(SpriteRenderer spriteRenderer, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.zoom = 1f;
        this.zoomAcc = 0.02f;
    }

    @Override
    public boolean update() {
        this.zoom -= zoomAcc;
        return this.zoom < 0f;
    }

    @Override
    public void renderFrom(SpriteRenderer spriteRenderer, TextureRegion texture_from) {
        if(zoom > 0f) {
            spriteRenderer.draw(texture_from,
                    MathUtils.round(screenWidth * (1f - zoom) * 0.5f),
                    MathUtils.round(screenHeight * (1f - zoom) * 0.5f),
                    MathUtils.round(screenWidth * zoom),
                    MathUtils.round(screenHeight * zoom));
        }
    }

    @Override
    public void renderTo(SpriteRenderer spriteRenderer, TextureRegion texture_to) {
        spriteRenderer.setColor(Color.GRAY);
        spriteRenderer.draw(texture_to, 0, 0);
    }

    @Override
    public void finished(SpriteRenderer spriteRenderer) {

    }

}
