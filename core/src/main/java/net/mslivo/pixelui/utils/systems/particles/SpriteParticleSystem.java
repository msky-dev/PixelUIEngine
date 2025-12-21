package net.mslivo.pixelui.utils.systems.particles;

import com.badlogic.gdx.math.MathUtils;
import net.mslivo.pixelui.media.*;
import net.mslivo.pixelui.rendering.SpriteRenderer;
import net.mslivo.pixelui.utils.systems.particles.particles.*;


public final class SpriteParticleSystem<T> extends ParticleSystem<T> {

    public interface RenderHook<T> {

        default void renderImageParticle(MediaManager mediaManager, SpriteRenderer spriteRenderer, ImageParticle<T> particle){
            CMediaImage cMediaImage = (CMediaImage) particle.sprite;
            spriteRenderer.setColor(particle.r, particle.g, particle.b, particle.a);
            spriteRenderer.drawCMediaImage(cMediaImage, MathUtils.round(particle.x), MathUtils.round(particle.y), particle.origin_x, particle.origin_y,
                    mediaManager.imageWidth(cMediaImage), mediaManager.imageHeight(cMediaImage),
                    particle.scaleX, particle.scaleY, particle.rotation);
            spriteRenderer.reset();
        }

        default void renderArrayParticle(MediaManager mediaManager, SpriteRenderer spriteRenderer, ArrayParticle<T> particle){
            CMediaArray cMediaArray = (CMediaArray) particle.sprite;
            spriteRenderer.setColor(particle.r, particle.g, particle.b, particle.a);
            spriteRenderer.drawCMediaArray(cMediaArray, particle.arrayIndex, MathUtils.round(particle.x), MathUtils.round(particle.y), particle.origin_x, particle.origin_y,
                    mediaManager.arrayWidth(cMediaArray), mediaManager.arrayHeight(cMediaArray),
                    particle.scaleX, particle.scaleY, particle.rotation);
            spriteRenderer.reset();
        }
        default void renderAnimationParticle(MediaManager mediaManager, SpriteRenderer spriteRenderer, AnimationParticle<T> particle, float animation_timer){
            CMediaAnimation cMediaAnimation = (CMediaAnimation) particle.sprite;
            spriteRenderer.setColor(particle.r, particle.g, particle.b, particle.a);
            spriteRenderer.drawCMediaAnimation(cMediaAnimation, (animation_timer + particle.animationOffset), MathUtils.round(particle.x), MathUtils.round(particle.y), particle.origin_x, particle.origin_y,
                    mediaManager.animationWidth(cMediaAnimation), mediaManager.animationHeight(cMediaAnimation),
                    particle.scaleX, particle.scaleY, particle.rotation);
            spriteRenderer.reset();
        }

        default void renderTextParticle(MediaManager mediaManager, SpriteRenderer spriteRenderer, TextParticle<T> particle){
            spriteRenderer.setColor(particle.r, particle.g, particle.b, particle.a);
            spriteRenderer.drawCMediaFont(particle.font, MathUtils.round(particle.x), MathUtils.round(particle.y), particle.text, 0, particle.text.length(), particle.centerX, particle.centerY);
            spriteRenderer.reset();
        }

        default void renderEmptyParticle(MediaManager mediaManager, SpriteRenderer spriteRenderer, EmptyParticle<T> particle){
            return;
        }


    }

    private static final RenderHook DEFAULT_RENDER_HOOK = new RenderHook() {
    };

    private RenderHook renderHook;

    public SpriteParticleSystem(Class<T> dataClass, ParticleUpdater<T> particleUpdater) {
        this(dataClass, particleUpdater, Integer.MAX_VALUE,DEFAULT_RENDER_HOOK);
    }

    public SpriteParticleSystem(Class<T> dataClass, ParticleUpdater<T> particleUpdater, int maxParticles) {
        this(dataClass, particleUpdater, maxParticles, DEFAULT_RENDER_HOOK);
    }

    public SpriteParticleSystem(Class<T> dataClass, ParticleUpdater<T> particleUpdater, int maxParticles, RenderHook<T> renderHook) {
        super(dataClass, particleUpdater, maxParticles);
        this.renderHook = renderHook != null ? renderHook : DEFAULT_RENDER_HOOK;
    }


    public void render(MediaManager mediaManager, SpriteRenderer spriteRenderer) {
        render(mediaManager, spriteRenderer, 0);
    }

    public void render(MediaManager mediaManager, SpriteRenderer spriteRenderer, float animation_timer) {
        if (super.numParticles == 0) return;
        spriteRenderer.saveState();
        for (int i = 0; i < particles.size; i++) {
            Particle<T> particle = particles.get(i);
            if (!particle.visible) continue;

            switch (particle) {
                case ImageParticle<T> imageParticle -> this.renderHook.renderImageParticle(mediaManager, spriteRenderer, imageParticle);
                case ArrayParticle<T> arrayParticle -> this.renderHook.renderArrayParticle(mediaManager, spriteRenderer, arrayParticle);
                case AnimationParticle<T> animationParticle -> this.renderHook.renderAnimationParticle(mediaManager, spriteRenderer, animationParticle, animation_timer);
                case TextParticle<T> textParticle -> this.renderHook.renderTextParticle(mediaManager, spriteRenderer, textParticle);
                case EmptyParticle emptyParticle -> this.renderHook.renderEmptyParticle(mediaManager, spriteRenderer, emptyParticle);
                default ->
                        throw new IllegalStateException("Invalid particle type: " + particle.getClass().getSimpleName());
            }
        }

        spriteRenderer.loadState();
    }

    public ImageParticle<T> addImageParticle(CMediaImage cMediaImage, float x, float y) {
        return addImageParticle(cMediaImage, x, y, 0.5f, 0.5f, 0.5f, 1f, 0f, 0f, 1f, 1f, 0f, true);
    }

    public ImageParticle<T> addImageParticle(CMediaImage cMediaImage, float x, float y, float r, float g, float b, float a) {
        return addImageParticle(cMediaImage, x, y, r, g, b, a, 0f, 0f, 1f, 1f, 0f, true);
    }

    public ImageParticle<T> addImageParticle(CMediaImage cMediaImage, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y) {
        return addImageParticle(cMediaImage, x, y, r, g, b, a, origin_x, origin_y, 1f, 1f, 0f, true);
    }

    public ImageParticle<T> addImageParticle(CMediaImage cMediaImage, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY) {
        return addImageParticle(cMediaImage, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, 0f, true);
    }

    public ImageParticle<T> addImageParticle(CMediaImage cMediaImage, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation) {
        return addImageParticle(cMediaImage, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, rotation, true);
    }

    public ImageParticle<T> addImageParticle(CMediaImage cMediaImage, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation, boolean visible) {
        if (!canAddParticle())
            return null;
        ImageParticle<T> particle = getNextImageParticle(cMediaImage, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, rotation, visible);
        addParticleToSystem(particle);
        return particle;
    }

    public ArrayParticle<T> addArrayParticle(CMediaArray cMediaArray, int arrayIndex, float x, float y) {
        return addArrayParticle(cMediaArray, arrayIndex, x, y, 0.5f, 0.5f, 0.5f, 1.0f, 0f, 0f, 1f, 1f, 0f, true);
    }

    public ArrayParticle<T> addArrayParticle(CMediaArray cMediaArray, int arrayIndex, float x, float y, float r, float g, float b, float a) {
        return addArrayParticle(cMediaArray, arrayIndex, x, y, r, g, b, a, 0f, 0f, 1f, 1f, 0f, true);
    }

    public ArrayParticle<T> addArrayParticle(CMediaArray cMediaArray, int arrayIndex, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y) {
        return addArrayParticle(cMediaArray, arrayIndex, x, y, r, g, b, a, origin_x, origin_y, 1f, 1f, 0f, true);
    }

    public ArrayParticle<T> addArrayParticle(CMediaArray cMediaArray, int arrayIndex, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY) {
        return addArrayParticle(cMediaArray, arrayIndex, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, 0f, true);
    }

    public ArrayParticle<T> addArrayParticle(CMediaArray cMediaArray, int arrayIndex, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation) {
        return addArrayParticle(cMediaArray, arrayIndex, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, rotation, true);
    }

    public ArrayParticle<T> addArrayParticle(CMediaArray cMediaArray, int arrayIndex, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation, boolean visible) {
        if (!canAddParticle())
            return null;
        ArrayParticle<T> particle = getNextArrayParticle(cMediaArray, arrayIndex, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, rotation, visible);
        addParticleToSystem(particle);
        return particle;
    }

    public AnimationParticle<T> addAnimationParticle(CMediaAnimation cMediaAnimation, float animationOffset, float x, float y) {
        return addAnimationParticle(cMediaAnimation, animationOffset, x, y, 0.5f, 0.5f, 0.5f, 1f, 0f, 0f, 1f, 1f, 0f, true);
    }

    public AnimationParticle<T> addAnimationParticle(CMediaAnimation cMediaAnimation, float animationOffset, float x, float y, float r, float g, float b, float a) {
        return addAnimationParticle(cMediaAnimation, animationOffset, x, y, r, g, b, a, 0f, 0f, 1f, 1f, 0f, true);
    }

    public AnimationParticle<T> addAnimationParticle(CMediaAnimation cMediaAnimation, float animationOffset, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y) {
        return addAnimationParticle(cMediaAnimation, animationOffset, x, y, r, g, b, a, origin_x, origin_y, 1f, 1f, 0f, true);
    }

    public AnimationParticle<T> addAnimationParticle(CMediaAnimation cMediaAnimation, float animationOffset, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY) {
        return addAnimationParticle(cMediaAnimation, animationOffset, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, 0f, true);
    }

    public AnimationParticle<T> addAnimationParticle(CMediaAnimation cMediaAnimation, float animationOffset, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation) {
        return addAnimationParticle(cMediaAnimation, animationOffset, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, rotation, true);
    }

    public AnimationParticle<T> addAnimationParticle(CMediaAnimation cMediaAnimation, float animationOffset, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation, boolean visible) {
        if (!canAddParticle())
            return null;
        AnimationParticle<T> particle = getNextAnimationParticle(cMediaAnimation, animationOffset, x, y, r, g, b, a, origin_x, origin_y, scaleX, scaleY, rotation, visible);
        addParticleToSystem(particle);
        return particle;
    }

    public TextParticle<T> addTextParticle(CMediaFont cMediaFont, String text, float x, float y) {
        return addTextParticle(cMediaFont, text, x, y, 0.5f, 0.5f, 0.5f, 1f, false, false, true);
    }

    public TextParticle<T> addTextParticle(CMediaFont cMediaFont, String text, float x, float y, float r, float g, float b, float a) {
        return addTextParticle(cMediaFont, text, x, y, r, g, b, a, false, false, true);
    }

    public TextParticle<T> addTextParticle(CMediaFont cMediaFont, String text, float x, float y, float r, float g, float b, float a, boolean centerX, boolean centerY) {
        return addTextParticle(cMediaFont, text, x, y, r, g, b, a, centerX, centerY, true);
    }

    public TextParticle<T> addTextParticle(CMediaFont cMediaFont, String text, float x, float y, float r, float g, float b, float a, boolean centerX, boolean centerY, boolean visible) {
        if (!canAddParticle())
            return null;
        TextParticle<T> particle = getNextTextParticle(cMediaFont, text, x, y, r, g, b, a, centerX, centerY, visible);
        addParticleToSystem(particle);
        return particle;
    }

    private ImageParticle<T> getNextImageParticle(CMediaImage sprite, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation, boolean visible) {

        ImageParticle<T> particle = (ImageParticle<T>) getParticleFromPool(ImageParticle.class);
        if (particle == null)
            particle = new ImageParticle<>();
        super.particleSetParticleData(particle, x, y, r, g, b, a, visible);
        particleSetTextureBasedParticleData(particle, origin_x, origin_y, scaleX, scaleY, rotation);
        particle.sprite = sprite;
        return particle;
    }

    private ArrayParticle<T> getNextArrayParticle(CMediaArray sprite, int arrayIndex, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation, boolean visible) {
        ArrayParticle<T> particle = (ArrayParticle<T>) getParticleFromPool(ArrayParticle.class);
        if (particle == null)
            particle = new ArrayParticle<>();
        super.particleSetParticleData(particle, x, y, r, g, b, a, visible);
        particleSetTextureBasedParticleData(particle, origin_x, origin_y, scaleX, scaleY, rotation);
        particle.sprite = sprite;
        particle.arrayIndex = arrayIndex;
        return particle;
    }

    private AnimationParticle<T> getNextAnimationParticle(CMediaAnimation sprite, float animationOffset, float x, float y, float r, float g, float b, float a, float origin_x, float origin_y, float scaleX, float scaleY, float rotation, boolean visible) {
        AnimationParticle<T> particle = (AnimationParticle<T>) getParticleFromPool(AnimationParticle.class);
        if (particle == null)
            particle = new AnimationParticle<>();
        super.particleSetParticleData(particle, x, y, r, g, b, a, visible);
        particleSetTextureBasedParticleData(particle, origin_x, origin_y, scaleX, scaleY, rotation);
        particle.sprite = sprite;
        particle.animationOffset = animationOffset;
        return particle;
    }

    private TextParticle<T> getNextTextParticle(CMediaFont font, String text, float x, float y, float r, float g, float b, float a, boolean centerX, boolean centerY, boolean visible) {
        TextParticle<T> particle = (TextParticle<T>) getParticleFromPool(TextParticle.class);
        if (particle == null)
            particle = new TextParticle<>();
        super.particleSetParticleData(particle, x, y, r, g, b, a, visible);
        particle.font = font;
        particle.text = text;
        particle.centerX = centerX;
        particle.centerY = centerY;
        return particle;
    }

    private void particleSetTextureBasedParticleData(TextureBasedParticle<T> textureBasedParticle, float origin_x, float origin_y, float scaleX, float scaleY, float rotation) {
        textureBasedParticle.origin_x = origin_x;
        textureBasedParticle.origin_y = origin_y;
        textureBasedParticle.scaleX = scaleX;
        textureBasedParticle.scaleY = scaleY;
        textureBasedParticle.rotation = rotation;
    }

    public RenderHook getRenderHook() {
        return renderHook;
    }

    public void setRenderHook(RenderHook renderHook) {
        this.renderHook = renderHook;
    }

}
