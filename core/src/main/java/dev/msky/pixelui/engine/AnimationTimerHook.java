package dev.msky.pixelui.engine;

import com.badlogic.gdx.Gdx;

public abstract class AnimationTimerHook {
    private float animationTimer;

    public void updateAnimationTimer(){
        animationTimer += Gdx.graphics.getDeltaTime();
    };

    public float getAnimationTimer(){
        return animationTimer;
    };
}
