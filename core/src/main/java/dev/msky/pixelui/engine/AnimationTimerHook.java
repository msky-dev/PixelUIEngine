package dev.msky.pixelui.engine;

public abstract class AnimationTimerHook {
    private float animationTimer;

    public void updateAnimationTimer(){
        animationTimer += 1/60f;
    };

    public float getAnimationTimer(){
        return animationTimer;
    };
}
