package dev.msky.pixelui.utils.transitions.basic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import dev.msky.pixelui.utils.transitions.TRANSITION_RENDER_ORDER;
import dev.msky.pixelui.utils.transitions.TRANSITION_SPEED;
import dev.msky.pixelui.utils.transitions.Transition;
import dev.msky.pixelui.rendering.SpriteRenderer;

public class FallOutTransition extends Transition {
    private float yTo;
    private float ySpeed;
    private int screenHeight;

    public FallOutTransition() {
        super();
    }

    public FallOutTransition(TRANSITION_SPEED transitionSpeed) {
        super(transitionSpeed);
    }

    @Override
    public TRANSITION_RENDER_ORDER getRenderOrder() {
        return TRANSITION_RENDER_ORDER.TO_FIRST;
    }

    @Override
    public void init(SpriteRenderer spriteRenderer, int screenWidth, int screenHeight) {
        this.yTo = 0;
        this.screenHeight = screenHeight;
        this.ySpeed = MathUtils.round(this.screenHeight /80f);
    }

    @Override
    public boolean update() {
        ySpeed -= screenHeight /1400f;
        yTo += ySpeed;
        return yTo <= -screenHeight;
    }

    @Override
    public void renderFrom(SpriteRenderer spriteRenderer, TextureRegion texture_from) {
        spriteRenderer.setColor(Color.GRAY);
        spriteRenderer.draw(texture_from, 0, MathUtils.round(yTo));
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
