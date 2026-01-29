package dev.msky.pixelui.utils.transitions.basic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import dev.msky.pixelui.utils.transitions.TRANSITION_RENDER_ORDER;
import dev.msky.pixelui.utils.transitions.TRANSITION_SPEED;
import dev.msky.pixelui.utils.transitions.Transition;
import dev.msky.pixelui.rendering.SpriteRenderer;

public class ImmediateTransition extends Transition {

    public ImmediateTransition(){
        super(TRANSITION_SPEED.IMMEDIATE);
    }

    @Override
    public TRANSITION_RENDER_ORDER getRenderOrder() {
        return TRANSITION_RENDER_ORDER.FROM_FIRST;
    }

    @Override
    public void init(SpriteRenderer spriteRenderer, int screenWidth, int screenHeight) {

    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public void renderFrom(SpriteRenderer spriteRenderer, TextureRegion texture_from) {
    }

    @Override
    public void renderTo(SpriteRenderer spriteRenderer, TextureRegion texture_to) {
    }

    @Override
    public void finished(SpriteRenderer spriteRenderer) {

    }

}
